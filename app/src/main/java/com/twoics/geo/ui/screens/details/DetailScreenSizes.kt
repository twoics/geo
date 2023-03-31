package com.twoics.geo.ui.screens.details

import androidx.compose.ui.unit.Dp

class DetailScreenSizes(screenWidth: Dp) {
    private var _maxWidth: Dp

    private val _contentWidthShare: Float = 0.03f
    private val _iconRightPaddingShare: Float = 0.03f
    private val _iconSizeShare: Float = 0.12f
    private val _descriptionTitlePaddingShare: Float = 0.03f
    private val _descriptionContentPaddingShare: Float = 0.04f
    private val _sheetPeakHeightShare: Float = 0.52f
    private val _sheetCornerShare: Float = 0.05f
    init {
        _maxWidth = screenWidth
    }
    val contentWidth: Dp
        get() = _maxWidth * _contentWidthShare

    val iconRightPadding: Dp
        get() = _maxWidth * _iconRightPaddingShare

    val iconSize: Dp
        get() = _maxWidth * _iconSizeShare

    val descriptionTitlePadding: Dp
        get() = _maxWidth * _descriptionTitlePaddingShare

    val descriptionContentPadding: Dp
        get() = _maxWidth * _descriptionContentPaddingShare

    val sheetPeakHeight: Dp
        get() = _maxWidth * _sheetPeakHeightShare

    val sheetCorner: Dp
        get() = _maxWidth * _sheetCornerShare

}
