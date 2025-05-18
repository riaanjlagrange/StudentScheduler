package com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.appointment.data.repository.AppointmentRepositoryImpl
import kotlinx.coroutines.launch
import arrow.core.Either
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import com.riaanjlagrange.studentschedulerapp.core.data.repository.UsersRepositoryImp
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirestoreError

class BookingViewModel : ViewModel() {
    private val appointmentRepo = AppointmentRepositoryImpl()
    private val usersRepo = UsersRepositoryImp()

    private var currentRole: UserRole? = null


    var state by mutableStateOf(BookingViewState())
        private set

    fun updateDate(date: String) {
        state = state.copy(appointment = state.appointment.copy(date = date))
    }

    fun updateTime(time: String) {
        state = state.copy(appointment = state.appointment.copy(time = time))
    }

    fun loadUser() {
        state = state.copy(yourUserIsLoading = true)

        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch

            val result = usersRepo.getUserById(userId)

            result.fold(
                ifLeft = { error ->
                    state = state.copy(yourUserError = error.error.message, yourUserIsLoading = false)
                },
                ifRight = { user ->
                    state = state.copy(yourUser = user, yourUserIsLoading = false)
                }
            )
        }
    }


    fun updateSelectedUser(user: AuthUser) {
        state = state.copy(
            selectedUser = user,
            appointment = when (currentRole) {
                UserRole.Student -> {
                    state.appointment.copy(lecturer = user)
                }

                UserRole.Lecturer -> state.appointment.copy(student = user)
                else -> state.appointment
            }
        )
    }

    fun updateYourUserToState(selectedRole: UserRole) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        currentRole = selectedRole

        viewModelScope.launch {
            val yourUser = when (selectedRole) {
                UserRole.Student -> usersRepo.getStudentById(userId)
                UserRole.Lecturer -> usersRepo.getLecturerById(userId)
            }

            yourUser.fold(
                ifLeft = { error ->
                    state = state.copy(userOptionsIsLoading = false, userOptionsError = error.error.message)
                },
                ifRight = { user ->
                    state = state.copy(
                        appointment = when (selectedRole) {
                            UserRole.Student -> state.appointment.copy(student = user)
                            UserRole.Lecturer -> state.appointment.copy(lecturer = user)
                        }
                    )
                }
            )
        }
    }

    fun loadUserOptionsForBooking(selectedRole: UserRole) {
        currentRole = selectedRole

        state = state.copy(userOptionsIsLoading = true)

        viewModelScope.launch {
            val result = when (selectedRole) {
                UserRole.Student -> usersRepo.getAllLecturers()
                UserRole.Lecturer -> usersRepo.getAllStudents()
            }

            result.fold(
                ifLeft = { error ->
                    state = state.copy(userOptionsIsLoading = false, userOptionsError = error.error.message)
                },
                ifRight = { users ->
                    state = state.copy(userOptionsIsLoading = false, userOptions = users)
                }
            )
        }
    }


    fun book(onResult: (Boolean) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val updatedAppointment = state.appointment.copy(userId = userId)

        state = state.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val response: Either<FirestoreError, String> =
                appointmentRepo.bookAppointment(updatedAppointment)

            response.fold(
                ifLeft = { error ->
                    state = state.copy(
                        isLoading = false,
                        error = error.error.message
                    )
                    onResult(false)
                },
                ifRight = {
                    state = state.copy(
                        isLoading = false,
                        error = null
                    )
                    onResult(true)
                }
            )
        }
    }
}
