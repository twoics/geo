package com.twoics.geo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.twoics.geo.ui.shared.AppBar
import com.twoics.geo.ui.shared.BottomBar


@Composable
@Preview
fun Bookmarks() {
    MaterialTheme {
        Scaffold(
            topBar = {
                AppBar()
            },
            bottomBar = {
                BottomBar()
            }
        ) { contentPadding ->
            // Screen content
            Box(modifier = Modifier.padding(contentPadding)) {
                /* ... */
            }
        }
    }
}