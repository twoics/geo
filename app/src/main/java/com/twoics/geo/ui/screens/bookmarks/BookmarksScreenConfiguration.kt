package com.twoics.geo.ui.screens.bookmarks

import androidx.compose.ui.unit.Dp

class BookmarksScreenConfiguration(screenWidth: Dp) {
    private var _maxWidth: Dp

    private val _cardPaddingShare: Float = 0.04f
    private val _cardElevationShare: Float = 0.03f
    private val _cardCornerSizeShare: Float = 0.04f
    private val _rowPaddingShare: Float = 0.04f
    private val _rowHeightShare: Float = 0.12f
    private val _iconRightPaddingShare: Float = 0.02f
    private val _iconSizeShare: Float = 0.07f
    private val _contentWidthShare: Float = 0.6f

    init {
        this._maxWidth = screenWidth
    }

    val cardPadding: Dp
        get() = _maxWidth * _cardPaddingShare

    val cardElevation: Dp
        get() = _maxWidth * _cardElevationShare

    val cardCorner: Dp
        get() = _maxWidth * _cardCornerSizeShare

    val rowPadding: Dp
        get() = _maxWidth * _rowPaddingShare

    val rowHeight: Dp
        get() = _maxWidth * _rowHeightShare

    val iconRightPadding: Dp
        get() = _maxWidth * _iconRightPaddingShare

    val iconSize: Dp
        get() = _maxWidth * _iconSizeShare

    val contentWidth: Dp
        get() = _maxWidth * _contentWidthShare
}