package com.emikhalets.moviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.moviesapp.model.network.ApiFactory
import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.pojo.ResultReview
import com.emikhalets.moviesapp.model.repository.ReviewListRepository
import kotlinx.coroutines.launch

class ReviewListViewModel : ViewModel() {

    private val repository = ReviewListRepository(ApiFactory.get())

    private val _reviews = MutableLiveData<List<ResultReview>>()
    val reviews get(): LiveData<List<ResultReview>> = _reviews

    private val _uiVisibility = MutableLiveData<Boolean>()
    val uiVisibility get(): LiveData<Boolean> = _uiVisibility

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    fun loadData(movieId: Int) {
        viewModelScope.launch {
            _uiVisibility.postValue(false)
            when (val response = repository.requestReviews(movieId)) {
                is ApiResult.Success -> {
                    _reviews.postValue(response.result.results)
                    _uiVisibility.postValue(true)
                }
                is ApiResult.Error -> _notice.postValue(response.msg)
                ApiResult.Loading -> {
                }
            }
        }
    }
}