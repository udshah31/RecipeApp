package com.example.search.domain.repository

import com.example.search.domain.model.Recipe
import com.example.search.domain.model.RecipeDetails
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getRecipe(query: String): Result<List<Recipe>>

    suspend fun getRecipeDetails(id: String): Result<RecipeDetails>

    suspend fun insertRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipe: Recipe)

    fun getAllRecipes(): Flow<List<Recipe>>
}