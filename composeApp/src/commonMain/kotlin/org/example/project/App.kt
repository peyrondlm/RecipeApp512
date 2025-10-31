package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.example.project.ui.RecipeTheme
import org.example.project.ui.screens.LoginScreenRoute
import org.example.project.ui.screens.MainScreen
import org.example.project.ui.screens.MainScreenGraph
import org.example.project.ui.screens.MainScreenRoute
import org.example.project.ui.screens.RegisterScreenRoute
import org.example.project.ui.screens.auth.LoginScreen
import org.example.project.ui.screens.auth.RegisterScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import recipeapp512.composeapp.generated.resources.Res
import recipeapp512.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    RecipeTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = MainScreenGraph
        ) {
            composable<RegisterScreenRoute> {
                RegisterScreen(
                    navController = navController
                )
            }

            composable<LoginScreenRoute> {
                LoginScreen(
                    navController = navController
                )
            }

            navigation<MainScreenGraph>(
                startDestination = MainScreenRoute
            ) {
                composable<MainScreenRoute>{
                    MainScreen()
                }
            }
        }
    }
}