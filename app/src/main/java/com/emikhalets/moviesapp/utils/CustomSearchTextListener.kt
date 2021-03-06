package com.emikhalets.moviesapp.utils

import android.widget.SearchView


open class CustomSearchTextListener : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String): Boolean {
        return onQueryTextSubmit(query)
    }

    override fun onQueryTextChange(newText: String): Boolean {
        return false
    }
}