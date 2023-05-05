package com.twoics.geo.data.repository

import com.twoics.geo.data.models.Bookmark
import com.twoics.geo.data.models.BookmarkType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestBookmarksRepository : IBookmarksRepository {
    private val bookmarks = arrayListOf<Bookmark>(
        Bookmark(
            id = 1,
            name = "Театр Пушкина",
            country = "Россиия",
            city = "Красноярск",
            street = "Мира",
            house = "24",
            lat = 11,
            long = 22,
            description = "Test",
            type = BookmarkType.CULTURE
        ),
        Bookmark(
            id = 2,
            name = "Vo Gan Udon",
            country = "Россиия",
            city = "Красноярск",
            street = "Ленина",
            house = "11",
            lat = 25,
            long = 65,
            description = "Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test",
            type = BookmarkType.FOOD
        ),
//        Bookmark(
//            id = 3,
//            name = "Столбы",
//            country = "Россиия",
//            city = "Красноярск",
//            street = "Красной армии",
//            house = "125",
//            lat = 112,
//            long = 252,
//            description = "Test Test Test Test Test Test Test Test Test Test Test Test Test",
//            type = BookmarkType.NATURE
//        ),
//        Bookmark(
//            id = 4,
//            name = "ИКИТ",
//            country = "Россиия",
//            city = "Красноярск",
//            street = "Академика Киренского",
//            house = "24",
//            lat = 15,
//            long = 62,
//            description = "Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test",
//            type = BookmarkType.SPORT
//        ),
    )

    override suspend fun insertBookmark(bookmark: Bookmark) {
        bookmarks.add(bookmark);
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        this.bookmarks.removeAt(bookmark.id)
    }

    override suspend fun getById(id: Int): Bookmark? {
        return this.bookmarks[id];
    }

    override fun getAll(): Flow<List<Bookmark>> {
        return flowOf(this.bookmarks)
    }
}