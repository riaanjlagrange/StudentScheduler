package com.riaanjlagrange.studentschedulerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.riaanjlagrange.studentschedulerapp.navigation.AppNavGraph
import com.riaanjlagrange.studentschedulerapp.ui.theme.StudentSchedulerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FirebaseApp.initializeApp(this)

        setContent {
            StudentSchedulerAppTheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }
    }
}