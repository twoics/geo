package com.twoics.geo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twoics.geo.data.repository.TestBookmarksRepository
import com.twoics.geo.ui.screens.bookmarks.BookmarksScreen
import com.twoics.geo.ui.screens.bookmarks.BookmarksViewModel
import com.twoics.geo.ui.screens.details.DetailsScreen
import com.twoics.geo.ui.screens.details.DetailsViewModel
import com.twoics.geo.ui.screens.search.SearchScreen
import com.twoics.geo.ui.screens.search.SearchViewModel
import com.twoics.geo.ui.shared.dto.TransmitBookmarkViewModel
import com.twoics.geo.ui.theme.GeoTheme
import com.twoics.geo.utils.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = TestBookmarksRepository()

        setContent {
            GeoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = Routes.BOOKMARKS) {
                        composable(Routes.SEARCH) {
                            SearchScreen(
                                SearchViewModel(),
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            ).Screen()
                        }
                        composable("bookmarks") {
                            BookmarksScreen(
                                BookmarksViewModel(
                                    repository = repository,
                                    transmitViewModel = TransmitBookmarkViewModel
                                ),
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            ).Screen()
                        }
                        composable("details") {
                            DetailsScreen(
                                DetailsViewModel(
                                    repository = repository,
                                    transmitViewModel = TransmitBookmarkViewModel,
                                ),
                            ).Screen()
                        }
                    }
                }
            }
        }
    }
}
