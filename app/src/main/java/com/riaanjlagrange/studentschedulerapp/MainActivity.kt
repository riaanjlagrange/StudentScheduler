package com.riaanjlagrange.studentschedulerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.riaanjlagrange.studentschedulerapp.navigation.AppNavGraph
import com.riaanjlagrange.studentschedulerapp.navigation.BottomNav
import com.riaanjlagrange.studentschedulerapp.ui.theme.StudentSchedulerAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        var keepSplashScreen = true
        super.onCreate(savedInstanceState)
        splashscreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            delay(3000)
            keepSplashScreen = false
        }

        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            StudentSchedulerAppTheme {
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route
                val bottomBarScreens = listOf(
                    BottomNav.Dashboard,
                    BottomNav.Bookings,
                    BottomNav.Feedback,
                    BottomNav.Calender
                )
                val shouldShowBottomBar = bottomBarScreens.any { it.route == currentRoute }
                Scaffold(

                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    when {
                                        currentRoute?.startsWith("login") == true -> "Login"
                                        currentRoute?.startsWith("register") == true -> "Register"
                                        currentRoute?.startsWith("dashboard") == true -> "Dashboard"
                                        currentRoute?.startsWith("booking") == true -> "Book Appointment"
                                        currentRoute?.startsWith("view_bookings") == true -> "My Bookings"
                                        currentRoute?.startsWith("feedback") == true -> "Feedback"
                                        currentRoute?.startsWith("calendar") == true -> "My Calendar"
                                        else -> "Stadio Scheduler"
                                    }
                                )
                            },
                            navigationIcon = {
                                if (navController.previousBackStackEntry != null
                                    && currentRoute?.startsWith("dashboard") == false
                                    && currentRoute.startsWith("view_bookings") == false
                                    && currentRoute.startsWith("feedback") == false
                                    && currentRoute.startsWith("calendar") == false
                                ) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            },
                            actions = {
                                if (currentRoute != null) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        Text("Switch Role")
                                        IconButton(onClick = {
                                            FirebaseAuth.getInstance().signOut()

                                            navController.navigate("role_select") {
                                                popUpTo(0) { inclusive = true } // clears backstack
                                            }
                                        }) {
                                            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                                        }
                                    }
                                }
                            },
                        )
                    },

                    bottomBar = {
                        if (shouldShowBottomBar) {
                            NavigationBar {
                                bottomBarScreens.forEach { screen ->
                                    NavigationBarItem(
                                        icon = {
                                            Icon(
                                                Icons.Default.Home,
                                                contentDescription = null
                                            )
                                        },
                                        label = { Text(screen.title) },
                                        selected = currentRoute == screen.route,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { paddingValues ->
                    AppNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
            }
    }
}