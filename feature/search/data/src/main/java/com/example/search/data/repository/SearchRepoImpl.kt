package com.example.search.data.repository

import com.example.search.data.local.RecipeDao
import com.example.search.data.mappers.toDomain
import com.example.search.data.remote.SearchApiService

import com.example.search.domain.model.Recipe
import com.example.search.domain.model.RecipeDetails
import com.example.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchRepoImpl(
    private val searchApiService: SearchApiService,
    private val recipeDao: RecipeDao
) : SearchRepository {

    override suspend fun getRecipe(query: String): Result<List<Recipe>> {
        return try {
            val response = searchApiService.getRecipe(query)
            if (response.isSuccessful) {
                response.body()?.meals?.let {
                    Result.success(it.toDomain())
                } ?: run { Result.failure(Exception("Error occurred")) }
            } else {
                Result.failure(Exception("Error occurred"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun getRecipeDetails(id: String): Result<RecipeDetails> {
        return try {
            val response = searchApiService.getRecipeDetails(query = id)
            if (response.isSuccessful) {
                response.body()?.meals?.let {
                    if (it.isNotEmpty()) {
                        Result.success(it.first().toDomain())
                    } else {
                        Result.failure(Exception("Error occurred"))
                    }
                } ?: run { Result.failure(Exception("Error occurred")) }
            } else {
                Result.failure(Exception("Error occurred"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(recipe = recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe = recipe)
    }

    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes()
    }
}