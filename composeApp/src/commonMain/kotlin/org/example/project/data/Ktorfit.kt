package org.example.project.data

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.data.services.AuthService
import org.example.project.data.services.RecipeService
import org.example.project.data.services.createAuthService
import org.example.project.data.services.createRecipeService

object ktorfitClient{
    val httpClient = HttpClient{
        expectSuccess = false
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }

            )
        }
        install(HttpTimeout){
            requestTimeoutMillis = 40000
            socketTimeoutMillis = 40000
            connectTimeoutMillis = 40000
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }


    val baseUrl = "https://recipes.pjasoft.com/api/"
    private val ktorfit = Ktorfit
        .Builder()
        .baseUrl(baseUrl)
        .httpClient(httpClient)
        .build()

    fun createAuthService(): AuthService{
        return ktorfit.createAuthService()
    }

    fun createRecipeService(): RecipeService{
        return ktorfit.createRecipeService()
    }
}