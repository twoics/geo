package com.twoics.geo.ui.screens.search

import androidx.compose.ui.unit.Dp

class SearchScreenConfiguration(screenWidth: Dp) {
    private var _maxWidth: Dp

    private val _filterButtonHorizontalPaddingShare: Float = 0.03f
    private val _filterButtonVerticalPaddingShare: Float = 0.01f
    private val _filterButtonWidthShare: Float = 0.16f
    private val _filterButtonHeightShare: Float = 0.19f
    private val _filterButtonCornerShare: Float = 0.04f
    private val _filterButtonIconShare: Float = 0.1f

    private val _buttonsHorizontalPaddingsShare: Float = 0.05f
    private val _buttonsVerticalPaddingsShare: Float = 0.03f
    private val _sheetMaxHeightShare: Float = 0.4f

    private val _radiusHorizontalPaddingShare: Float = 0.05f
    private val _sheetPeekHeightShare: Float = 0.3f
    private val _sheetShapeCornerShare: Float = 0.05f
    private val _backgroundContentHeightShare: Float = 0.9f

    init {
        _maxWidth = screenWidth
    }

    val filterButtonHorizontalPadding: Dp
        get() = _maxWidth * _filterButtonHorizontalPaddingShare

    val filterButtonVerticalPadding: Dp
        get() = _maxWidth * _filterButtonVerticalPaddingShare

    val filterButtonWidth: Dp
        get() = _maxWidth * _filterButtonWidthShare

    val filterButtonHeight: Dp
        get() = _maxWidth * _filterButtonHeightShare

    val filterButtonCorner: Dp
        get() = _maxWidth * _filterButtonCornerShare

    val filterButtonIcon: Dp
        get() = _maxWidth * _filterButtonIconShare

    val buttonsHorizontalPadding: Dp
        get() = _maxWidth * _buttonsHorizontalPaddingsShare

    val buttonsVerticalPadding: Dp
        get() = _maxWidth * _buttonsVerticalPaddingsShare

    val sheetMaxHeight: Float
        get() = _sheetMaxHeightShare

    val sliderHorizontalPadding: Dp
        get() = _maxWidth * _radiusHorizontalPaddingShare

    val sheetPeakHeight: Dp
        get() = _maxWidth * _sheetPeekHeightShare

    val sheetCorner: Dp
        get() = _maxWidth * _sheetShapeCornerShare

    val backgroundHeight: Float
        get() = _backgroundContentHeightShare

    val maxWidth: Dp
        get() = _maxWidth
}
