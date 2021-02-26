package com.emikhalets.moviesapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.moviesapp.model.network.ApiFactory
import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.repository.ActivityRepository
import kotlinx.coroutines.launch

class ActivityViewModel : ViewModel() {

    private val repository = ActivityRepository(ApiFactory.get())

    fun fillApiConfig(width: Int) {
        viewModelScope.launch {
            when (val config = repository.requestConfiguration()) {
                is ApiResult.Success -> {
                    val data = config.result.images
                    Log.d(this.javaClass.name, "fillApiConfig: $width")
                }
                is ApiResult.Error -> {
                }
                ApiResult.Loading -> {
                }
            }
        }
    }
}