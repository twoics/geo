package com.twoics.geo.data.repository

import com.twoics.geo.data.dao.BookmarksDao
import com.twoics.geo.data.models.Bookmark

class BookmarksRepository(
    private val dao: BookmarksDao
) : IBookmarksRepository {
    override suspend fun insertBookmark(bookmark: Bookmark) {
        dao.insertBookmark(bookmark)
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        dao.deleteBookmark(bookmark)
    }

    override suspend fun getById(id: Int): Bookmark? {
        return dao.getById(id)
    }

    override fun getAll(): List<Bookmark> {
        return dao.getAll()
    }
}