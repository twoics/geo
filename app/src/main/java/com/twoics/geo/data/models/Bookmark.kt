package com.twoics.geo.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

enum class BookmarkType {
    CULTURE,
    FOOD,
    NATURE,
    SPORT
}

@Entity
@Parcelize
data class Bookmark(
    @PrimaryKey
    val id: Int,
    var name: String,
    var country: String,
    var city: String,
    var street: String,
    var house: String,
    var lat: Int,
    var long: Int,
    var description: String,
    var type: BookmarkType
) : Parcelable
