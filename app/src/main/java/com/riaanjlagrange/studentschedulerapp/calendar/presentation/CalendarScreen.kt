import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.riaanjlagrange.studentschedulerapp.calendar.presentation.CalendarViewModel
import io.github.boguszpawlowski.composecalendar.SelectableCalendar

@Composable
fun CalendarScreen(
    navController: NavController,
    viewModel: CalendarViewModel = viewModel()
) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.loadUser()
        viewModel.loadAppointments()
    }
    Spacer(modifier = Modifier.height(4.dp))
    Card(
        modifier = Modifier
            .size(50.dp)
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        SelectableCalendar(modifier = Modifier.padding(30.dp))
    }
}