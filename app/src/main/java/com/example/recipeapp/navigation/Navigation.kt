package com.example.recipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.common.navigation.NavigationSubGraphRoute


@Composable
fun RecipeNavigation(
    modifier: Modifier = Modifier,
    navigationSubGraphs: NavigationSubGraphs
) {

    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = NavigationSubGraphRoute.Search.route
    ) {
        navigationSubGraphs.searchFeatureApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )

        navigationSubGraphs.mediaPlayerApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )


    }

}