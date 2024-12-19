package com.example.search.ui.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.UiText
import com.example.search.domain.model.Recipe
import com.example.search.domain.use_cases.DeleteRecipeUseCase
import com.example.search.domain.use_cases.GetAllRecipesFromLocalDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getAllRecipesFromLocalDbUseCase: GetAllRecipesFromLocalDbUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase
) : ViewModel() {

    private var originalList = mutableListOf<Recipe>()

    private val _uiSate = MutableStateFlow(FavouriteScreen.UiState())
    val uiState: StateFlow<FavouriteScreen.UiState> get() = _uiSate.asStateFlow()

    private val _navigation = Channel<FavouriteScreen.Navigation>()
    val navigation: Flow<FavouriteScreen.Navigation> = _navigation.receiveAsFlow()


    init {
        getRecipeList()
    }


    fun onEvent(event: FavouriteScreen.Event) {
        when (event) {
            FavouriteScreen.Event.AlphabeticalSort -> alphabeticalSort()

            FavouriteScreen.Event.LessIngredientsSort -> lessIngredientsSort()

            FavouriteScreen.Event.ResetSort -> resetSort()


            is FavouriteScreen.Event.ShowDetails -> viewModelScope.launch {
                _navigation.send(FavouriteScreen.Navigation.GoToRecipeDetailsScreen(event.id))
            }

            is FavouriteScreen.Event.DeleteRecipe -> deleteRecipe(event.recipe)

            is FavouriteScreen.Event.GoToDetails -> viewModelScope.launch {
                _navigation.send(FavouriteScreen.Navigation.GoToRecipeDetailsScreen(event.id))
            }
        }
    }

    private fun deleteRecipe(recipe: Recipe) = deleteRecipeUseCase.invoke(recipe)


    private fun getRecipeList() = viewModelScope.launch {
        getAllRecipesFromLocalDbUseCase.invoke().collectLatest { list ->
            originalList = list.toMutableList()
            _uiSate.update { FavouriteScreen.UiState(data = list) }
        }
    }


    fun alphabeticalSort() =
        _uiSate.update { FavouriteScreen.UiState(data = originalList.sortedBy { it.strMeal }) }

    fun lessIngredientsSort() =
        _uiSate.update { FavouriteScreen.UiState(data = originalList.sortedBy { it.strInstructions.length }) }


    fun resetSort() = _uiSate.update { FavouriteScreen.UiState(data = originalList) }


}

object FavouriteScreen {
    data class UiState(
        val isLoading: Boolean = false,
        val error: UiText = UiText.Idle,
        val data: List<Recipe>? = null
    )

    sealed interface Navigation {
        data class GoToRecipeDetailsScreen(val id: String) : Navigation
    }

    sealed interface Event {
        data object AlphabeticalSort : Event
        data object LessIngredientsSort : Event
        data object ResetSort : Event
        data class ShowDetails(val id: String) : Event
        data class DeleteRecipe(val recipe: Recipe) : Event
        data class GoToDetails(val id: String) : Event
    }
}

