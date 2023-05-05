package com.twoics.geo.data.dao

import androidx.room.*
import com.twoics.geo.data.models.Bookmark

@Dao
interface IBookmarksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmark WHERE id = :id")
    suspend fun getById(id: Int): Bookmark?

    @Query("SELECT * FROM bookmark")
    fun getAll(): List<Bookmark>
}
