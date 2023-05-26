package com.twoics.geo.nav

object Routes {
    const val BOOKMARKS = "bookmarks"
    const val DETAILS = "details"
    const val SEARCH = "search"

    fun contains(route: String): Boolean {
        return route in listOf(BOOKMARKS, DETAILS, SEARCH)
    }
}