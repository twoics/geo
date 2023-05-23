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
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var country: String,
    var city: String,
    var street: String,
    var house: String,
    var lat: Double,
    var long: Double,
    var description: String,
    var type: BookmarkType
) : Parcelable
