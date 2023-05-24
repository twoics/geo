package com.twoics.geo.ui.screens.details

import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.core.text.HtmlCompat.fromHtml
import com.twoics.geo.R
import com.twoics.geo.data.models.Bookmark
import com.twoics.geo.ui.shared.screen.IBottomBar
import com.twoics.geo.ui.shared.screen.IScreen
import com.twoics.geo.utils.PlaceIcons

class DetailsScreen(
    private var viewModel: DetailsViewModel,
    private val bottomBar: IBottomBar
) : IScreen {
    private lateinit var sizes: DetailScreenSizes

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Screen() {
        MaterialTheme {
            BoxWithConstraints {
                sizes = DetailScreenSizes(this.maxWidth)

                Scaffold(
                    topBar = {
                        TopBar(viewModel::onEvent)
                    },
                    bottomBar = {
                        bottomBar.ComposableBottomBar()
                    }

                ) { contentPadding ->
                    // Screen content
                    Box(modifier = Modifier.padding(contentPadding)) {
                        val sheetState = rememberBottomSheetState(
                            initialValue = BottomSheetValue.Collapsed
                        )
                        val scaffoldState = rememberBottomSheetScaffoldState(
                            bottomSheetState = sheetState
                        )
                        BottomSheetScaffold(
                            scaffoldState = scaffoldState,
                            sheetContent = {
                                viewModel.bookmark?.let { SheetContent(it) }
                            },

                            sheetPeekHeight = sizes.sheetPeakHeight,
                            sheetShape = RoundedCornerShape(
                                sizes.sheetCorner,
                                sizes.sheetCorner,
                                0.dp,
                                0.dp
                            ),
                        ) {
                            BackgroundContent()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TopBar(onEvent: (DetailsEvent) -> Unit) {
        Column {
            TopAppBar(title = {
                Text("Details")
            }, navigationIcon = {
                IconButton(onClick = {
                    onEvent(DetailsEvent.BackButtonClick)
                }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

    @Composable
    private fun TypeIcon(bookmark: Bookmark) {
        Column(
            modifier = Modifier.padding(0.dp, 0.dp, sizes.iconRightPadding, 0.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                painter = painterResource(PlaceIcons.getIconId(bookmark.type)),
                tint = Color.Unspecified,
                modifier = Modifier.size(sizes.iconSize),
                contentDescription = null
            )
        }
    }

    @Composable
    private fun PlaceInfo(
        name: String,
        city: String,
        country: String,
        street: String,
        house: String
    ) {
        Column {
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$country, $city, $street $house",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }

    @Composable
    private fun LikeButton(
        bookmark: Bookmark,
        onEvent: (DetailsEvent) -> Unit
    ) {
        var placeFromDataBase = true
        if (bookmark.id == null) {
            placeFromDataBase = false
        }

        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Button(
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),

                onClick = {
                    if (!placeFromDataBase) {
                        onEvent(
                            DetailsEvent.LikeButtonClick(
                                bookmark = bookmark
                            )
                        )
                    } else {
                        onEvent(
                            DetailsEvent.ViewAtMapButtonClick(
                                bookmark = bookmark
                            )
                        )
                    }
                },

            ) {
                Icon(if (placeFromDataBase) Icons.Filled.Map else Icons.Filled.Favorite, contentDescription = null)
            }
        }
    }

    @Composable
    private fun Description(
        description: String
    ) {
        @Composable
        fun HtmlText(html: String, modifier: Modifier = Modifier) {
            AndroidView(
                modifier = modifier,
                factory = { context -> TextView(context) },
                update = { it.text = fromHtml(html, FROM_HTML_MODE_COMPACT) }
            )
        }

        Text(
            modifier = Modifier.padding(sizes.descriptionTitlePadding),
            text = "Description",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        val scroll = rememberScrollState(0)
        HtmlText(
            html = description,
            modifier = Modifier
                .verticalScroll(scroll)
                .padding(sizes.descriptionContentPadding)
        )
    }

    @Composable
    fun SheetContent(bookmark: Bookmark) {
        BoxWithConstraints {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                backgroundColor = Color.White
            ) {
                Column {
                    Row(
                        Modifier
                            .padding(sizes.contentPaddings)
                    ) {

                        TypeIcon(bookmark)
                        Box(
                            Modifier.width(sizes.placeWidth)
                        ) {
                            PlaceInfo(
                                name = bookmark.name,
                                country = bookmark.country,
                                city = bookmark.city,
                                street = bookmark.street,
                                house = bookmark.house
                            )

                        }
                        LikeButton(bookmark, viewModel::onEvent)
                    }

                    Description(
                        description = bookmark.description
                    )
                }
            }
        }
    }

    @Composable
    private fun BackgroundContent() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(sizes.backgroundShare)
        ) {
            Image(
                painterResource(R.drawable.backdata),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
