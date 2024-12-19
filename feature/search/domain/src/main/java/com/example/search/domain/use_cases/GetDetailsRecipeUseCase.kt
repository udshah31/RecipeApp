package com.example.search.domain.use_cases

import com.example.common.utils.NetworkResult
import com.example.search.domain.model.RecipeDetails
import com.example.search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetDetailsRecipeUseCase @Inject constructor(
    private val searchRepository: SearchRepository
){

    operator fun invoke(query:String) = flow<NetworkResult<RecipeDetails>>{

        emit(NetworkResult.Loading())
        val response = searchRepository.getRecipeDetails(query)
        if (response.isSuccess){
            emit(NetworkResult.Success(data = response.getOrNull()))
        }else{
            emit(NetworkResult.Error(message = response.exceptionOrNull()?.localizedMessage))
        }

    }.catch {
        emit(NetworkResult.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}