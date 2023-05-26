package com.twoics.geo.data.dao

import androidx.room.*
import com.twoics.geo.data.models.Bookmark

@Dao
interface BookmarksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmark(bookmark: Bookmark)

    @Delete
    fun deleteBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmark WHERE id = :id")
    fun getById(id: Int): Bookmark?

    @Query("SELECT * FROM bookmark")
    fun getAll(): List<Bookmark>
}
