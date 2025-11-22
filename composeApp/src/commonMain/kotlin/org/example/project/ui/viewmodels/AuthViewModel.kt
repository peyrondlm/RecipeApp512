package org.example.project.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.project.data.ktorfitClient
import org.example.project.domain.dtos.Login
import org.example.project.domain.dtos.Register
import org.example.project.domain.utils.Preferences

class AuthViewModel : ViewModel(){
    fun register(
        name : String,
        email : String,
        password : String,
        onResult: (Boolean, String) -> Unit
    ){
        viewModelScope.launch {
            try {
                val service = ktorfitClient.createAuthService()
                val register = Register(
                    name = name,
                    email = email,
                    password = password
                )
                val result = service.register(register)
                if(result.isLogged){
                    println("Logueado")
                    Preferences.saveUserId(result.userId)
                    Preferences.saveIsLogged(result.isLogged)
                    onResult(true,result.message)
                    println(result.toString())
                }else{
                    onResult(false,result.message)
                    println("No logueao")
                    println(result.toString())
                }
            }catch (e : Exception){
                onResult(false, "Error al registrarte")
                print(e.toString())
            }
        }
    }

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ){
        viewModelScope.launch {
            try {
                val service = ktorfitClient.createAuthService()
                val login = Login(
                    email = email,
                    password = password
                )
                val result = service.login(login)
                if (result.isLogged){
                    Preferences.saveUserId(result.userId)
                    Preferences.saveIsLogged(result.isLogged)
                    onResult(true, result.message)
                }
                else{
                    onResult(false, result.message )
                }
            }
            catch (e :  Exception){
                onResult(false, "Error al loguearse")
            }
        }
    }
}