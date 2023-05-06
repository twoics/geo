package com.twoics.geo.ui.shared.dto

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.twoics.geo.data.models.Bookmark

object TransmitBookmarkViewModel : ViewModel(), IBookmarkTransmit {
    private var bookmark by mutableStateOf<Bookmark?>(null)

    override fun get(): Bookmark? {
        return bookmark
    }

    override fun set(newBookmark: Bookmark) {
        bookmark = newBookmark
    }
}