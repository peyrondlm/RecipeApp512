package org.example.project.data.services

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import org.example.project.domain.dtos.AuthResponse
import org.example.project.domain.dtos.Login
import org.example.project.domain.dtos.Register

interface AuthService {
    @POST("auth/register")
    suspend fun register(@Body register: Register) : AuthResponse

    @POST("auth/login")
    suspend fun login(@Body login: Login) : AuthResponse
}