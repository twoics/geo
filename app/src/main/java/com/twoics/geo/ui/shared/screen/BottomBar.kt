package com.twoics.geo.ui.shared.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.twoics.geo.nav.INavigation
import com.twoics.geo.nav.Routes

class BottomBar(
    private val navigation: INavigation
) : IBottomBar {
    private data class NavigationData(
        val titles: List<String> = listOf(Routes.SEARCH, Routes.BOOKMARKS),
        val icons: List<ImageVector> = listOf(Icons.Filled.Search, Icons.Filled.Favorite)
    )

    @Composable
    private fun Navigation() {
        var selectedItem by remember { mutableStateOf(0) }
        val navigationData = NavigationData()

        return BottomNavigation {
            navigationData.titles.forEachIndexed { index, item ->
                BottomNavigationItem(
                    icon = { Icon(navigationData.icons[index], contentDescription = null) },
                    label = { Text(item) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navigation.navigate(item)
                    }
                )
            }
        }
    }


    @Composable
    override fun ComposableBottomBar() {
        return BottomAppBar(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Navigation()
        }
    }
}
