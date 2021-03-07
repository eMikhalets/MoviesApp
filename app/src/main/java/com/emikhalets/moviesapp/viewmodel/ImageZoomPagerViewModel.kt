package com.emikhalets.moviesapp.viewmodel

import androidx.lifecycle.ViewModel

class ImageZoomPagerViewModel : ViewModel() {

    var images = listOf<String>()
    var position = 0
}