package com.crypto.chat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.crypto.chat.ui.LoginScreen

@Composable
fun NavGraph(navController: NavHostController) {
     NavHost(navController = navController, startDestination = "profile") {
        composable( Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable("main") { }

    }
}
