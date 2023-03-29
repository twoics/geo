package com.twoics.geo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
//@Preview
fun AppBar(backRoute: String, navController: NavController) {
    Column {
        TopAppBar(title = {
            Text("App bar")
        }, navigationIcon = {
            IconButton(onClick = {
                navController.navigate(backRoute)
            }) {
                Icon(Icons.Filled.ArrowBack, null)
            }
        },
            modifier = Modifier
                .fillMaxWidth()
        )

    }
}