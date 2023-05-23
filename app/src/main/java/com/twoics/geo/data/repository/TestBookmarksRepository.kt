package com.twoics.geo.data.repository

import com.twoics.geo.data.models.Bookmark
import com.twoics.geo.data.models.BookmarkType

class TestBookmarksRepository : IBookmarksRepository {
    private val bookmarks = arrayListOf<Bookmark>(
        Bookmark(
            id = 1,
            name = "Театр Пушкина",
            country = "Россиия",
            city = "Красноярск",
            street = "Мира",
            house = "24",
            lat = 11.0,
            long = 22.0,
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit Morbi ac massa vehicula magna fringilla tempus.Morbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempus..",
            type = BookmarkType.CULTURE
        ),
        Bookmark(
            id = 2,
            name = "Vo Gan Udon",
            country = "Россиия",
            city = "Красноярск",
            street = "Ленина",
            house = "11",
            lat = 25.0,
            long = 65.0,
            description = "Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test",
            type = BookmarkType.FOOD
        ),
        Bookmark(
            id = 3,
            name = "Столбы",
            country = "Россиия",
            city = "Красноярск",
            street = "Красной армии",
            house = "125",
            lat = 112.0,
            long = 252.0,
            description = "Test Test Test Test Test Test Test Test Test Test Test Test Test",
            type = BookmarkType.NATURE
        ),
        Bookmark(
            id = 4,
            name = "ИКИТ",
            country = "Россиия",
            city = "Красноярск",
            street = "Академика Киренского",
            house = "24",
            lat = 15.0,
            long = 62.0,
            description = "Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test",
            type = BookmarkType.SPORT
        )
    )

    override suspend fun insertBookmark(bookmark: Bookmark) {
        fun maxId(): Int {
            var min = bookmarks.first().id
            bookmarks.forEach { bookmark ->
                if (bookmark.id!! < min!!) {
                    min = bookmark.id
                }
            }
            return min!!
        }

        bookmark.id = maxId() + 1
        bookmarks.add(bookmark);
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        val deleteMark: Bookmark = bookmark.id?.let { get(it) } ?: return

        this.bookmarks.removeAll {
            it.id == deleteMark.id
        }
    }

    override suspend fun getById(id: Int): Bookmark? {
        return get(id)
    }

    override fun getAll(): List<Bookmark> {
        return this.bookmarks
    }

    private fun get(id: Int): Bookmark? {
        return this.bookmarks.find {
            it.id == id
        }
    }
}