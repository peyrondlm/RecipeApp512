package org.example.project.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.example.project.domain.utils.Preferences
import org.example.project.ui.components.FullScreenLoading
import org.example.project.ui.screens.HomeScreenRoute
import org.example.project.ui.screens.LoginScreenRoute
import org.example.project.ui.screens.RegisterScreenRoute
import org.example.project.ui.viewmodels.AuthViewModel
import kotlin.reflect.KClass

@Composable
fun LoginScreen(
    navController: NavController
){
    var isLogged by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val colors = MaterialTheme.colorScheme

    val viewModel: AuthViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return AuthViewModel() as T
            }
        }
    )

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    isLogged = Preferences.getIsLogged()

    LaunchedEffect(isLogged){
        if(isLogged){
            navController.navigate(HomeScreenRoute){
                popUpTo(LoginScreenRoute){ inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ){
        Column(modifier = Modifier.fillMaxSize()){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                    .background(colors.primary)
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .height(350.dp)
                .padding(horizontal = 24.dp)
                .shadow(10.dp,RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(colors.surface)
                .padding(vertical = 10.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Bienvenido")
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                shape = CircleShape,
                onValueChange = { email = it },
                singleLine = true,
                enabled = !isLoading,
                placeholder = { Text(text = "Correo Electronico") }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                shape = CircleShape,
                onValueChange = { password = it },
                singleLine = true,
                enabled = !isLoading,
                placeholder = { Text(text = "Contraseña") },
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = {
                    isLoading = true
                    viewModel.login(
                        email = email,
                        password = password
                    ){ result, message ->
                        isLoading = false
                        if (result) isLogged = true
                        else {
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ){
                Text(text = "Iniciar Sesion")
            }
            Text(
                text = "¿No tienes una cuenta? Crea una",
                color = colors.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(enabled = !isLoading) {
                    navController.navigate(RegisterScreenRoute)
                }
            )
        }

        if (isLoading) {
            FullScreenLoading(message = "Cargando...")
        }
    }
}
