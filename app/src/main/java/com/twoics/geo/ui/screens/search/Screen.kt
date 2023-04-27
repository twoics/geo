package com.twoics.geo.ui.screens.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.twoics.geo.R
import com.twoics.geo.data.models.BookmarkType
import com.twoics.geo.ui.shared.AppBar
import com.twoics.geo.ui.shared.BottomBar


@Composable
fun FilterButton(
    icon: Painter,
    backgroundColor: Color,
    sizes: SearchScreenSizes,
    viewModel: SearchViewModel,
    bookmarkType: BookmarkType
) {
    Column(
        Modifier
            .padding(sizes.filterButtonHorizontalPadding, sizes.filterButtonVerticalPadding)
            .size(sizes.filterButtonWidth, sizes.filterButtonHeight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val selected = remember { mutableStateOf(false) }

        Button(
            onClick = {
                selected.value = !selected.value
                viewModel.onEvent(
                    SearchEvent.FilterButtonClicked(bookmarkType)
                )
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor,
                contentColor = backgroundColor
            ),
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(sizes.filterButtonCorner),
            border = if (selected.value) BorderStroke(1.dp, Color.Black) else null

        ) {

            Icon(
                icon,
                tint = Color.Unspecified,
                modifier = Modifier.size(sizes.filterButtonIcon),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun CultureButton(viewModel: SearchViewModel, sizes: SearchScreenSizes) {
    val icon = painterResource(id = R.drawable.arch)
    val backgroundColor = Color(0xFFFFE9E9)

    FilterButton(
        icon = icon,
        backgroundColor = backgroundColor,
        sizes = sizes,
        viewModel,
        BookmarkType.CULTURE
    )
}

@Composable
private fun FoodButton(viewModel: SearchViewModel, sizes: SearchScreenSizes) {
    val icon = painterResource(id = R.drawable.food)
    val backgroundColor = Color(0xFFFFF4E8)

    FilterButton(
        icon = icon,
        backgroundColor = backgroundColor,
        sizes = sizes,
        viewModel,
        BookmarkType.FOOD
    )
}

@Composable
private fun NatureButton(viewModel: SearchViewModel, sizes: SearchScreenSizes) {
    val icon = painterResource(id = R.drawable.nature)
    val backgroundColor = Color(0xFFEAFFF2)

    FilterButton(
        icon = icon,
        backgroundColor = backgroundColor,
        sizes = sizes,
        viewModel,
        BookmarkType.NATURE
    )
}

@Composable
private fun SportButton(viewModel: SearchViewModel, sizes: SearchScreenSizes) {
    val icon = painterResource(id = R.drawable.sport)
    val backgroundColor = Color(0xFFEEF7FF)

    FilterButton(
        icon = icon,
        backgroundColor = backgroundColor,
        sizes = sizes,
        viewModel,
        BookmarkType.SPORT
    )
}

@Composable
private fun RadiusSlider(
    viewModel: SearchViewModel,
    sizes: SearchScreenSizes
) {
    Column(
        modifier = Modifier.padding(sizes.sliderHorizontalPadding, 0.dp)
    )
    {
        Text(
            text = "Radius",
            modifier = Modifier.padding(0.dp, sizes.sliderHorizontalPadding, 0.dp, 0.dp),
        )
        var sliderPosition by remember { mutableStateOf(0f) }
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                viewModel.onEvent(SearchEvent.RadiusChanged(it))
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            // TODO MIX MAX from ViewModel
            Text(
                text = "50m",
                modifier = Modifier.padding(0.dp),
                color = Color.Gray
            )
            Text(
                text = "2000m",
                modifier = Modifier.padding(0.dp),
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun SearchButton(viewModel: SearchViewModel) {
    ExtendedFloatingActionButton(
        onClick = {
            viewModel.onEvent(SearchEvent.OnSearchClick)
        },
        icon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Search"
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        text = { Text("Search") }
    )
}

@Composable
private fun SheetContent(viewModel: SearchViewModel, sizes: SearchScreenSizes) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(sizes.sheetMaxHeight)
            .shadow(25.dp),
        backgroundColor = Color.White
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        sizes.sliderHorizontalPadding,
                        sizes.buttonsVerticalPadding,
                        sizes.sliderHorizontalPadding,
                        sizes.buttonsVerticalPadding
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CultureButton(viewModel, sizes)
                FoodButton(viewModel, sizes)
                NatureButton(viewModel, sizes)
                SportButton(viewModel, sizes)
            }

            RadiusSlider(viewModel, sizes)
            SearchButton(viewModel)
        }
    }
}

@Composable
private fun BackgroundContent(sizes: SearchScreenSizes) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            painterResource(R.drawable.map),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(sizes.backgroundHeight)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel
) {
    MaterialTheme {
        BoxWithConstraints {
            val sizes = SearchScreenSizes(this.maxWidth)

            Scaffold(
                topBar = {
                    AppBar()
                },
                bottomBar = {
                    BottomBar()
                }

            ) { contentPadding ->
                // Screen content
                Box(
                    modifier = Modifier
                        .padding(contentPadding)
                ) {
                    val sheetState = rememberBottomSheetState(
                        initialValue = BottomSheetValue.Expanded
                    )
                    val scaffoldState = rememberBottomSheetScaffoldState(
                        bottomSheetState = sheetState
                    )
                    BottomSheetScaffold(
                        scaffoldState = scaffoldState,
                        sheetContent = {
                            SheetContent(viewModel, sizes)
                        },
                        sheetPeekHeight = sizes.sheetPeakHeight,
                        sheetShape = RoundedCornerShape(
                            sizes.sheetCorner,
                            sizes.sheetCorner,
                            0.dp,
                            0.dp
                        ),
                    ) {
                        BackgroundContent(sizes)
                    }
                }
            }
        }
    }
}
