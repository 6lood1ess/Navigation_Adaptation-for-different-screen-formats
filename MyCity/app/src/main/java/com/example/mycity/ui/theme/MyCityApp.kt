package com.example.mycity.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MyCityApp(
    windowSizeClass: WindowWidthSizeClass,
    viewModel: MyCityViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    if (windowSizeClass == WindowWidthSizeClass.Expanded) {
        val navigator = rememberListDetailPaneScaffoldNavigator()

        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                if (uiState.selectedCategory == null) {
                    CategoryListScreen(
                        categories = uiState.categories,
                        onCategoryClick = { viewModel.selectCategory(it) }
                    )
                } else {
                    PlaceListScreen(
                        category = uiState.selectedCategory!!,
                        places = uiState.places,
                        onPlaceClick = { viewModel.selectPlace(it) },
                        onBackClick = { viewModel.navigateBackToHome() }
                    )
                }
            },
            detailPane = {
                if (uiState.selectedPlace != null) {
                    PlaceDetailScreen(
                        place = uiState.selectedPlace!!,
                        onBackClick = { viewModel.navigateBackToList() }
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize())
                }
            }
        )
    } else {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                CategoryListScreen(
                    categories = uiState.categories,
                    onCategoryClick = {
                        viewModel.selectCategory(it)
                        navController.navigate("places")
                    }
                )
            }
            composable("places") {
                if (uiState.selectedCategory != null) {
                    PlaceListScreen(
                        category = uiState.selectedCategory!!,
                        places = uiState.places,
                        onPlaceClick = {
                            viewModel.selectPlace(it)
                            navController.navigate("details")
                        },
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
            composable("details") {
                if (uiState.selectedPlace != null) {
                    PlaceDetailScreen(
                        place = uiState.selectedPlace!!,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}