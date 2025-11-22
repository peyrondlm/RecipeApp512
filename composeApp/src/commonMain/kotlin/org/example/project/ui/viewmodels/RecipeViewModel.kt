package org.example.project.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.project.data.ktorfitClient
import org.example.project.domain.dtos.Prompt
import org.example.project.domain.dtos.RecipeDTO
import org.example.project.domain.models.Recipe
import org.example.project.domain.utils.Preferences

class RecipeViewModel : ViewModel() {
    val userId = Preferences.getUserId()
    val recipeService = ktorfitClient.createRecipeService()

    var recipes by mutableStateOf<List<Recipe>>(listOf())

    var generatedRecipe by mutableStateOf<RecipeDTO?>(null)

    var showSheet by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    init {
        getRecipes()
    }

    fun showModalFromList(recipe : RecipeDTO){
        generatedRecipe = recipe
        showSheet = true
    }

    fun hideModal(){
        showSheet = false
    }

    fun generateRecipe(prompt: Prompt){
        viewModelScope.launch {
            try {
                isLoading = true
                val result = recipeService.generateRecipe(prompt)
                showSheet = true
                generatedRecipe = result
                println(result.toString())
            }
            catch (e : Exception){
                showSheet = false
                println(e.toString())
            }
            finally {
                isLoading = false
            }
        }
    }

    fun getRecipes(){
        viewModelScope.launch {
            try {
                val result = recipeService.getRecipesByUserId(userId)
                recipes = result.takeLast(5).reversed()
                println(result.toString())
            }
            catch (e : Exception){
                println(e.toString())
            }
        }
    }

    fun saveRecipeInDb(){
        viewModelScope.launch {
            try {
                val recipe = Recipe(
                    id = 0,
                    userId = userId,
                    category = generatedRecipe?.category ?: "",
                    imageUrl = generatedRecipe?.imageUrl ?: "",
                    ingredients = generatedRecipe?.ingredients ?: listOf(),
                    instructions = generatedRecipe?.instructions ?: listOf(),
                    minutes = generatedRecipe?.stars ?: 0,
                    stars = generatedRecipe?.stars ?: 0,
                    title = generatedRecipe?.title ?: ""
                )
                val result = recipeService.saveGeneratedRecipe(recipe = recipe)
                print(result.toString())
            }catch (e: Exception){
                println(e.toString())
            }
        }
    }
}