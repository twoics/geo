package com.twoics.geo.utils

import android.content.Context
import android.graphics.drawable.Drawable
import com.twoics.geo.R
import com.twoics.geo.data.models.Bookmark
import com.twoics.geo.data.models.BookmarkType

object PlaceIcons {
    fun getMapIcon(place: Bookmark, mapContext: Context): Drawable {
        val iconId = getIconId(place.type)
        return mapContext.resources.getDrawable(iconId)
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
