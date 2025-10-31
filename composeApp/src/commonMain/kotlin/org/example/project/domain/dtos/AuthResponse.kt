package org.example.project.domain.dtos

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val message : String,
    val isLogged : Boolean,
    val userId : Int
)
