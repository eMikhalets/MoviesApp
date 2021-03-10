package com.emikhalets.moviesapp.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.moviesapp.model.network.ApiFactory
import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.pojo.*
import com.emikhalets.moviesapp.model.repository.MovieDetailsRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel : ViewModel() {

    private val repository = MovieDetailsRepository(ApiFactory.get())

    private val _movie = MutableLiveData<ResponseMovieId>()
    val movie get(): LiveData<ResponseMovieId> = _movie

    private val _cast = MutableLiveData<List<Cast>>()
    val cast get(): LiveData<List<Cast>> = _cast

    private val _reviews = MutableLiveData<List<ResultReview>>()
    val reviews get(): LiveData<List<ResultReview>> = _reviews

    private val _moviesSimilar = MutableLiveData<List<ResultMovieList>>()
    val moviesSimilar get(): LiveData<List<ResultMovieList>> = _moviesSimilar

    private val _uiVisibility = MutableLiveData<Boolean>()
    val uiVisibility get(): LiveData<Boolean> = _uiVisibility

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    var id = -1
    var scrollPos = 0

    @SuppressLint("NullSafeMutableLiveData")
    fun loadMovieData(movieId: Int) {
        viewModelScope.launch {
            _uiVisibility.postValue(false)
            when (val response = repository.requestMovieById(movieId)) {
                is ApiResult.Success -> {
                    id = response.result.id
                    _movie.postValue(response.result)
                }
                is ApiResult.Error -> _notice.postValue(response.msg)
                ApiResult.Loading -> {
                }
            }
        }
    }

    fun loadOtherData() {
        viewModelScope.launch {
            if (id >= 0) {
                loadCast()
                loadReviews()
                loadMoviesSimilar()
                _uiVisibility.postValue(true)
            }
        }
    }

    private suspend fun loadCast() {
        when (val response = repository.requestCredits(id)) {
            is ApiResult.Success -> _cast.postValue(response.result.cast.take(3))
            is ApiResult.Error -> _notice.postValue(response.msg)
            ApiResult.Loading -> {
            }
        }
    }

    private suspend fun loadReviews() {
        when (val response = repository.requestReviews(id)) {
            is ApiResult.Success -> _reviews.postValue(response.result.results.take(3))
            is ApiResult.Error -> _notice.postValue(response.msg)
            ApiResult.Loading -> {
            }
        }
    }

    private suspend fun loadMoviesSimilar() {
        when (val response = repository.requestMoviesSimilar(id)) {
            is ApiResult.Success -> _moviesSimilar.postValue(response.result.results)
            is ApiResult.Error -> _notice.postValue(response.msg)
            ApiResult.Loading -> {
            }
        }
    }

    fun parseYear(release: String): String {
        return try {
            release.split("-").first()
        } catch (indexEx: IndexOutOfBoundsException) {
            indexEx.printStackTrace()
            "No year"
        }
    }
}