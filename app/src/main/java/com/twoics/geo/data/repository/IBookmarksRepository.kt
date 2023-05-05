package com.twoics.geo.data.repository

import com.twoics.geo.data.models.Bookmark
import kotlinx.coroutines.flow.Flow

interface IBookmarksRepository {
    suspend fun insertBookmark(bookmark: Bookmark)
    suspend fun deleteBookmark(bookmark: Bookmark)
    suspend fun getById(id: Int): Bookmark?
    fun getAll(): Flow<List<Bookmark>>
}