package com.twoics.geo.ui.shared.dto

import com.twoics.geo.data.models.Bookmark

interface IBookmarkTransmit {
    fun get(): Bookmark?
    fun set(newBookmark: Bookmark)
}