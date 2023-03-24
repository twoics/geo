package com.twoics.geo.ui.shared

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

private data class NavigationData(
    val titles: List<String> = listOf("Search", "Bookmarks"),
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
                onClick = { selectedItem = index }
            )
        }
    }
}


@Composable
@Preview
fun BottomBar() {
    return BottomAppBar {
        Navigation()
    }
}