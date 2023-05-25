package com.twoics.geo.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
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
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "country") var country: String,
    @ColumnInfo(name = "city") var city: String,
    @ColumnInfo(name = "street") var street: String,
    @ColumnInfo(name = "house") var house: String,
    @ColumnInfo(name = "latitude") var latitude: Double,
    @ColumnInfo(name = "longitude") var longitude: Double,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "type") var type: BookmarkType,
    @ColumnInfo(name = "imgURL") var imgURL: String = "https://img.freepik.com/free-vector/hand-drawn-404-error_23-2147730749.jpg?w=2000"
) : Parcelable
