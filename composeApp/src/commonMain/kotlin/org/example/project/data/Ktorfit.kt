package org.example.project.data

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.data.services.AuthService
import org.example.project.data.services.createAuthService

//HTTP 200
object ktorfitClient{

    //Basicamente
    val httpClient = HttpClient{
        expectSuccess = false //para permitir status code de error en la app
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true //Permite JSONs imperfectos
                    ignoreUnknownKeys = true // ignora las propiedades que no se quieren mapear
                }

            )
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
}