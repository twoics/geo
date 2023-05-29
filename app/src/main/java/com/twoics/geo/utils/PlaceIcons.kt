package com.twoics.geo.utils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.twoics.geo.R
import com.twoics.geo.data.models.BookmarkType
import com.twoics.geo.ui.theme.CULTURE_MARKER
import com.twoics.geo.ui.theme.FOOD_MARKER
import com.twoics.geo.ui.theme.NATURE_MARKER
import com.twoics.geo.ui.theme.SPORT_MARKER

object PlaceIcons {
    private val colorMap = mapOf<BookmarkType, Color>(
        BookmarkType.SPORT to SPORT_MARKER,
        BookmarkType.FOOD to FOOD_MARKER,
        BookmarkType.NATURE to NATURE_MARKER,
        BookmarkType.CULTURE to CULTURE_MARKER
    )

    fun getMapIcon(placeType: BookmarkType, mapContext: Context): Drawable {
        val icon = mapContext.resources.getDrawable(
            org.osmdroid.library.R.drawable.marker_default
        ).mutate()

        icon.colorFilter = PorterDuffColorFilter(colorMap[placeType]!!.toArgb(), PorterDuff.Mode.SRC_IN)
        return icon
    }

    fun getIconId(placeType: BookmarkType): Int {
        when (placeType) {
            BookmarkType.FOOD -> {
                return R.drawable.food
            }

            BookmarkType.SPORT -> {
                return R.drawable.sport
            }

            BookmarkType.CULTURE -> {
                return R.drawable.arch
            }

            BookmarkType.NATURE -> {
                return R.drawable.nature
            }
        }
    }
}
