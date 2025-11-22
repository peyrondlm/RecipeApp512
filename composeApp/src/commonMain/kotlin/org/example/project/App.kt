package org.example.project

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.ui.theme.RecipeTheme
import org.example.project.ui.screens.HomeScreenRoute
import org.example.project.ui.screens.LoginScreenRoute
import org.example.project.ui.screens.RegisterScreenRoute
import org.example.project.ui.screens.auth.LoginScreen
import org.example.project.ui.screens.auth.RegisterScreen
import org.example.project.ui.screens.homescreen.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    RecipeTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = LoginScreenRoute
        ){
            composable <RegisterScreenRoute>{
                RegisterScreen(
                    navController = navController
                )
            }
            composable <LoginScreenRoute>{
                LoginScreen(
                    navController = navController
                )
            }

            composable <HomeScreenRoute>{
                HomeScreen(navController)
            }
        }
    }
}