package com.twoics.geo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.twoics.geo.api.PlacesApi
import com.twoics.geo.data.database.AppDatabase
import com.twoics.geo.data.repository.BookmarksRepository
import com.twoics.geo.map.Map
import com.twoics.geo.map.MapDataTransfer
import com.twoics.geo.nav.Navigation
import com.twoics.geo.nav.Routes
import com.twoics.geo.ui.screens.bookmarks.BookmarksScreen
import com.twoics.geo.ui.screens.bookmarks.BookmarksViewModel
import com.twoics.geo.ui.screens.details.DetailsScreen
import com.twoics.geo.ui.screens.details.DetailsViewModel
import com.twoics.geo.ui.screens.search.SearchScreen
import com.twoics.geo.ui.screens.search.SearchViewModel
import com.twoics.geo.ui.shared.dto.TransmitBookmarkViewModel
import com.twoics.geo.ui.shared.screen.BottomBar
import com.twoics.geo.ui.shared.screen.IBottomBar
import com.twoics.geo.ui.theme.GeoTheme
import org.osmdroid.util.GeoPoint

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "places.db"
        ).allowMainThreadQueries().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val repository = BookmarksRepository(db.bookmarkDao())
        val mapDataTransfer = MapDataTransfer()
        val weatherApi = PlacesApi()

        val map = Map(
            defaultAreaRadius = 1000.0,
            defaultMapLocation = GeoPoint(56.0, 93.0),
            mapDataTransfer
        )

        setContent {
            GeoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val navigation = Navigation(navController)
                    val bottomBar: IBottomBar = BottomBar(navigation)

                    NavHost(navController, startDestination = Routes.SEARCH) {
                        composable(Routes.SEARCH) {
                            SearchScreen(
                                SearchViewModel(
                                    navigation = navigation,
                                    map = map,
                                    mapDataTransfer = mapDataTransfer,
                                    transmitViewModel = TransmitBookmarkViewModel,
                                    placesApi = weatherApi
                                ),
                                bottomBar = bottomBar
                            ).Screen()
                        }
                        composable(Routes.BOOKMARKS) {
                            BookmarksScreen(
                                BookmarksViewModel(
                                    navigation = navigation,
                                    repository = repository,
                                    transmitViewModel = TransmitBookmarkViewModel
                                ),
                                bottomBar = bottomBar
                            ).Screen()
                        }
                        composable(Routes.DETAILS) {
                            DetailsScreen(
                                DetailsViewModel(
                                    navigation = navigation,
                                    repository = repository,
                                    transmitViewModel = TransmitBookmarkViewModel,
                                ),
                                bottomBar = bottomBar
                            ).Screen()
                        }
                    }
                }
            }
        }
    }
}
