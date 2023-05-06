package com.twoics.geo.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoics.geo.data.models.Bookmark
import com.twoics.geo.data.repository.IBookmarksRepository
import com.twoics.geo.ui.shared.dto.IBookmarkTransmit
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: IBookmarksRepository,
    private var transmitViewModel: IBookmarkTransmit
) : ViewModel() {

    var bookmark: Bookmark? = null

    init {
        viewModelScope.launch {
            val transmitBookmark = transmitViewModel.get();
            bookmark = transmitBookmark
        }
    }
}