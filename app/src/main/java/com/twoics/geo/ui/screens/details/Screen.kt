package com.twoics.geo.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twoics.geo.R
import com.twoics.geo.data.models.Bookmark
import com.twoics.geo.ui.shared.AppBar
import com.twoics.geo.ui.shared.BottomBar
import com.twoics.geo.ui.shared.IScreen

class DetailsScreen(
    private var viewModel: DetailsViewModel
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
                        AppBar()
                    },
                    bottomBar = {
                        BottomBar()
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
    private fun TypeIcon() {
        Column(
            modifier = Modifier.padding(0.dp, 0.dp, sizes.iconRightPadding, 0.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                painter = painterResource(id = com.twoics.geo.R.drawable.arch),
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
    private fun LikeButton() {
        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Button(
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),

                onClick = { }
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }
        }
    }

    @Composable
    private fun Description(
        description: String
    ) {
        Text(
            modifier = Modifier.padding(sizes.descriptionTitlePadding),
            text = "Description",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        val scroll = rememberScrollState(0)
        Text(
            text = description,
            color = Color.Gray,
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

                        TypeIcon()
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
                        LikeButton()
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
