package com.twoics.geo.data.repository

import com.twoics.geo.data.models.Bookmark

interface IBookmarksRepository {
    suspend fun insertBookmark(bookmark: Bookmark)
    suspend fun deleteBookmark(bookmark: Bookmark)
    suspend fun getById(id: Int): Bookmark?
    fun getAll(): List<Bookmark>
}