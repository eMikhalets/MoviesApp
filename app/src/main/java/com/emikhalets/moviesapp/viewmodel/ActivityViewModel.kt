package com.emikhalets.moviesapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.moviesapp.model.network.ApiFactory
import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.repository.ActivityRepository
import com.emikhalets.moviesapp.utils.ApiImageConfig
import kotlinx.coroutines.launch

class ActivityViewModel : ViewModel() {

    private val repository = ActivityRepository(ApiFactory.get())

    fun fillApiConfig(width: Int) {
        viewModelScope.launch {
            when (val config = repository.requestConfiguration()) {
                is ApiResult.Success -> {
                    val data = config.result.images
                    Log.d(this.javaClass.name, "fillApiConfig: $width")
                    ApiImageConfig.baseUrl = data.base_url
                    ApiImageConfig.secureBaseUrl = data.secure_base_url
                    ApiImageConfig.backdropSize = data.secure_base_url
                    ApiImageConfig.logoSizes = data.base_url
                    ApiImageConfig.posterSizes = data.base_url
                    ApiImageConfig.profileSizes = data.base_url
                    ApiImageConfig.stillSizes = data.base_url
                }
                is ApiResult.Error -> {
                }
                ApiResult.Loading -> {
                }
            }
        }
    }
}