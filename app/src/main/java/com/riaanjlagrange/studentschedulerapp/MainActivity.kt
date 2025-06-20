package com.riaanjlagrange.studentschedulerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.navigation.AppNavGraph
import com.riaanjlagrange.studentschedulerapp.navigation.BottomNav
import com.riaanjlagrange.studentschedulerapp.ui.theme.PurplePrimaryVariant
import com.riaanjlagrange.studentschedulerapp.ui.theme.StudentSchedulerAppTheme
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
                                    text = when {
                                        currentRoute?.startsWith("login") == true -> "Login"
                                        currentRoute?.startsWith("register") == true -> "Register"
                                        currentRoute?.startsWith("dashboard") == true -> "Dashboard"
                                        currentRoute?.startsWith("booking") == true -> "Book Appointment"
                                        currentRoute?.startsWith("view_bookings") == true -> "My Bookings"
                                        currentRoute?.startsWith("view_feedback") == true -> "Feedback"
                                        currentRoute?.startsWith("calendar") == true -> "My Calendar"
                                        else -> "Stadio Scheduler"
                                    },
                                    color = Color.White
                                )
                            },
                            navigationIcon = {
                                if (navController.previousBackStackEntry != null) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back",
                                            tint = Color.White
                                        )
                                    }
                                }
                            },
                            actions = {
                                if (currentRoute != null) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "Switch Role",
                                            modifier = Modifier
                                                .clickable {
                                                    FirebaseAuth.getInstance().signOut()
                                                    navController.navigate("role_select") {
                                                        popUpTo(0) { inclusive = true }
                                                    }
                                                }
                                                .padding(10.dp),
                                            color = Color.White
                                        )
                                        Icon(
                                            Icons.AutoMirrored.Filled.ExitToApp,
                                            contentDescription = "Logout",
                                            tint = Color.White
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = PurplePrimaryVariant,
                            )
                        )
                    },
                    bottomBar = {
                        if (shouldShowBottomBar) {
                            NavigationBar(containerColor = PurplePrimaryVariant) {
                                bottomBarScreens.forEach { screen ->
                                    NavigationBarItem(
                                        icon = {
                                            when (screen.title) {
                                                "Home" -> Icon(Icons.Default.Home, contentDescription = null, tint = Color.White)
                                                "Bookings" -> Icon(Icons.Default.Info, contentDescription = null, tint = Color.White)
                                                "Feedback" -> Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
                                                "Calendar" -> Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.White)
                                            }
                                        },
                                        label = {
                                            Text(
                                                text = screen.title,
                                                color = Color.White
                                            )
                                        },
                                        alwaysShowLabel = true,
                                        selected = currentRoute == screen.route,
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = Color.Blue,
                                            unselectedIconColor = Color.Gray,
                                            selectedTextColor = Color.Blue,
                                            unselectedTextColor = Color.Gray,
                                            indicatorColor = Color.Transparent
                                        ),
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(0) { saveState = true }
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
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.schedulerbg),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        AppNavGraph(
                            navController = navController,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}