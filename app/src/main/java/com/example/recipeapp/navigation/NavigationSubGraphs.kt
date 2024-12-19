package com.example.recipeapp.navigation

import com.example.media_player.navigation.MediaPlayerFeatureApi
import com.example.search.ui.navigation.SearchFeatureApi

data class NavigationSubGraphs(
    val searchFeatureApi: SearchFeatureApi,
    val mediaPlayerApi : MediaPlayerFeatureApi
)