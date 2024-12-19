package com.example.search.domain.use_cases

import com.example.search.domain.repository.SearchRepository
import javax.inject.Inject

class GetAllRecipesFromLocalDbUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    operator fun invoke() = searchRepository.getAllRecipes()
}