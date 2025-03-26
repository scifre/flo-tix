 package com.example.e_ticket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_ticket.ui.theme.ETicketTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

 ///val db = FirebaseFirestore.getInstance()


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            val user = FirebaseAuth.getInstance().currentUser

            val startDestination = if(user != null) "home" else "login"
            //create nav host with two destinations home and login
            NavHost(navController = navController, startDestination = startDestination) {
                composable("home") { HomeScreen(navController = navController) }
                composable("scan") { QrScanner(navController = navController) }
                composable("login") { LoginScreen(navController = navController) }
                composable("result/{scannedCode}") {backStackEntry ->
                    //write code to display scanned code
                    val scannedCode = backStackEntry.arguments?.getString("scannedCode")
                    if (scannedCode != null) {
                        ResultScreen(navController = navController, scannedCode = scannedCode)
                    }
                }

            }
        }
    }
}

