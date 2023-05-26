package com.twoics.geo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.twoics.geo.data.dao.BookmarksDao
import com.twoics.geo.data.models.Bookmark


@Database(entities = [Bookmark::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarksDao
}