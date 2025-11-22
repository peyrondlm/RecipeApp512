package org.example.project.data.services

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Query
import org.example.project.domain.dtos.Prompt
import org.example.project.domain.dtos.RecipeDTO
import org.example.project.domain.models.Recipe

interface RecipeService {
    @POST("recipes/ai-generate")
    suspend fun generateRecipe(@Body prompt: Prompt) : RecipeDTO

    @GET("recipes")
    suspend fun getRecipesByUserId(@Query("userId") userId: Int ) : List<Recipe>

    @POST("recipes")
    suspend fun saveGeneratedRecipe(@Body recipe: Recipe) : Recipe
}