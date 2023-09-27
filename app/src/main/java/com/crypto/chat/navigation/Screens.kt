package com.crypto.chat.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object MainScreen : Screen("main_screen")
    object ProfileScreen : Screen("profile_screen")
}