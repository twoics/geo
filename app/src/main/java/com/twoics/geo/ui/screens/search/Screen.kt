package com.twoics.geo.ui.screens.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.twoics.geo.data.models.BookmarkType
import com.twoics.geo.ui.shared.screen.IBottomBar
import com.twoics.geo.ui.shared.screen.IScreen
import com.twoics.geo.utils.PlaceIcons
import org.osmdroid.views.MapView


private object ButtonsIcons {
    val SPORT_ICON: Painter
        @Composable
        get() = painterResource(PlaceIcons.getIconId(BookmarkType.SPORT))
    val NATURE_ICON: Painter
        @Composable
        get() = painterResource(PlaceIcons.getIconId(BookmarkType.NATURE))
    val FOOD_ICON: Painter
        @Composable
        get() = painterResource(PlaceIcons.getIconId(BookmarkType.FOOD))
    val CULTURE_ICON: Painter
        @Composable
        get() = painterResource(PlaceIcons.getIconId(BookmarkType.CULTURE))
}

private object ButtonsColors {
    val SPORT_BUTTON = Color(0xFFEEF7FF)
    val NATURE_BUTTON = Color(0xFFEAFFF2)
    val FOOD_BUTTON = Color(0xFFFFF4E8)
    val CULTURE_BUTTON = Color(0xFFFFE9E9)
}

class SearchScreen(
    private var viewModel: SearchViewModel,
    private val bottomBar: IBottomBar
) : IScreen {

    private lateinit var sizes: SearchScreenSizes

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Screen() {
        val scaffoldState = rememberScaffoldState()
        MaterialTheme {
            BoxWithConstraints {
                sizes = SearchScreenSizes(this.maxWidth)
                Scaffold(
                    topBar = {
                        TopBar()
                    },
                    bottomBar = {
                        bottomBar.ComposableBottomBar()
                    },
                    scaffoldState = scaffoldState

                ) { contentPadding ->
                    // Screen content
                    Box(
                        modifier = Modifier
                            .padding(contentPadding)
                    ) {
                        val sheetState = rememberBottomSheetState(
                            initialValue = BottomSheetValue.Expanded
                        )
                        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                            bottomSheetState = sheetState
                        )
                        BottomSheetScaffold(
                            scaffoldState = bottomSheetScaffoldState,
                            sheetContent = {
                                SheetContent()
                            },
                            sheetPeekHeight = sizes.sheetPeakHeight,
                            sheetShape = RoundedCornerShape(
                                sizes.sheetCorner,
                                sizes.sheetCorner,
                                0.dp,
                                0.dp
                            ),
                        ) {
                            MapContent(
                                Modifier.fillMaxSize(),
                                viewModel.mapView
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TopBar() {
        Column {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text("Search")
                        Row() {
                            val eyeOpen = remember { mutableStateOf(viewModel.isSearchAreaHidden) }

                            IconButton(onClick = {
                                viewModel.onEvent(SearchEvent.CleanMapClick)
                            }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Clear map")
                            }
                            IconButton(onClick = {
                                eyeOpen.value = !eyeOpen.value
                                viewModel.onEvent(SearchEvent.HideMapAreaClick(eyeOpen.value))
                            }) {
                                Icon(
                                    if (eyeOpen.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = "Hide radius"
                                )
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

    @Composable
    private fun FilterButton(
        icon: Painter,
        backgroundColor: Color,
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
    private fun RadiusSlider() {
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
                // TODO MIN MAX from ViewModel
                Text(
                    text = "${viewModel.minSearchRadius}m",
                    modifier = Modifier.padding(0.dp),
                    color = Color.Gray
                )
                Text(
                    text = "${viewModel.maxSearchRadius}m",
                    modifier = Modifier.padding(0.dp),
                    color = Color.Gray
                )
            }
        }
    }


    @Composable
    private fun SearchButton() {
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
    private fun SheetContent() {
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

                    FilterButton(
                        icon = ButtonsIcons.CULTURE_ICON,
                        backgroundColor = ButtonsColors.CULTURE_BUTTON,
                        bookmarkType = BookmarkType.CULTURE
                    )
                    FilterButton(
                        icon = ButtonsIcons.NATURE_ICON,
                        backgroundColor = ButtonsColors.NATURE_BUTTON,
                        bookmarkType = BookmarkType.NATURE
                    )
                    FilterButton(
                        icon = ButtonsIcons.SPORT_ICON,
                        backgroundColor = ButtonsColors.SPORT_BUTTON,
                        bookmarkType = BookmarkType.SPORT
                    )
                    FilterButton(
                        icon = ButtonsIcons.FOOD_ICON,
                        backgroundColor = ButtonsColors.FOOD_BUTTON,
                        bookmarkType = BookmarkType.FOOD
                    )
                }

                RadiusSlider()
                SearchButton()
            }
        }
    }

    @Composable
    private fun MapContent(
        modifier: Modifier,
        mapViewState: MapView,
        onLoad: ((map: MapView) -> Unit)? = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            AndroidView(
                { mapViewState },
                modifier
            ) { mapView -> onLoad?.invoke(mapView) }
        }
    }
}