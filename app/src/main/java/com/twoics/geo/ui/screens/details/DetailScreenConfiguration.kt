package com.twoics.geo.ui.screens.details

import androidx.compose.ui.unit.Dp

class DetailScreenConfiguration(screenWidth: Dp) {
    private var _maxWidth: Dp

    private val contentWidthShare: Float = 0.03f
    private val iconRightPaddingShare: Float = 0.03f
    private val iconSizeShare: Float = 0.12f
    private val descriptionTitlePaddingShare: Float = 0.03f
    private val descriptionContentPaddingShare: Float = 0.04f
    private val sheetPeakHeightShare: Float = 0.52f
    private val sheetCornerShare: Float = 0.05f
    init {
        _maxWidth = screenWidth
    }
    val contentWidth: Dp
        get() = _maxWidth * contentWidthShare

    val iconRightPadding: Dp
        get() = _maxWidth * iconRightPaddingShare

    val iconSize: Dp
        get() = _maxWidth * iconSizeShare

    val descriptionTitlePadding: Dp
        get() = _maxWidth * descriptionTitlePaddingShare

    val descriptionContentPadding: Dp
        get() = _maxWidth * descriptionContentPaddingShare

    val sheetPeakHeight: Dp
        get() = _maxWidth * sheetPeakHeightShare

    val sheetCorner: Dp
        get() = _maxWidth * sheetCornerShare

}
