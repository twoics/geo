package com.twoics.geo.ui.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
    return BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
    ) {
        Navigation()
    }
}