package com.emikhalets.moviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.moviesapp.model.network.ApiFactory
import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.pojo.MovieListResult
import com.emikhalets.moviesapp.model.pojo.PopArtistsResult
import com.emikhalets.moviesapp.model.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = HomeRepository(ApiFactory.get())

    private val _popArtists = MutableLiveData<List<PopArtistsResult>>()
    val popArtists get(): LiveData<List<PopArtistsResult>> = _popArtists

    private val _moviesPopular = MutableLiveData<List<MovieListResult>>()
    val moviesPopular get(): LiveData<List<MovieListResult>> = _moviesPopular

    private val _moviesNowPlaying = MutableLiveData<List<MovieListResult>>()
    val moviesNowPlaying get(): LiveData<List<MovieListResult>> = _moviesNowPlaying

    private val _moviesTopRated = MutableLiveData<List<MovieListResult>>()
    val moviesTopRated get(): LiveData<List<MovieListResult>> = _moviesTopRated

    private val _moviesUpcoming = MutableLiveData<List<MovieListResult>>()
    val moviesUpcoming get(): LiveData<List<MovieListResult>> = _moviesUpcoming

    private val _uiVisibility = MutableLiveData<Boolean>()
    val uiVisibility get(): LiveData<Boolean> = _uiVisibility

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    fun loadData() {
        viewModelScope.launch {
            _uiVisibility.postValue(false)
            loadPopArtists()
            loadMoviesPopular()
            loadMoviesNowPlaying()
            loadMoviesTopRated()
            loadMoviesUpcoming()
            _uiVisibility.postValue(true)
        }
    }

    private suspend fun loadPopArtists() {
        when (val response = repository.requestPersonPopular()) {
            is ApiResult.Success -> _popArtists.postValue(response.result.results)
            is ApiResult.Error -> _notice.postValue(response.msg)
            ApiResult.Loading -> {
            }
        }
    }

    private suspend fun loadMoviesPopular() {
        when (val response = repository.requestMoviesPopular()) {
            is ApiResult.Success -> _moviesPopular.postValue(response.result.results)
            is ApiResult.Error -> _notice.postValue(response.msg)
            ApiResult.Loading -> {
            }
        }
    }

    private suspend fun loadMoviesNowPlaying() {
        when (val response = repository.requestMoviesNowPlaying()) {
            is ApiResult.Success -> _moviesNowPlaying.postValue(response.result.results)
            is ApiResult.Error -> _notice.postValue(response.msg)
            ApiResult.Loading -> {
            }
        }
    }

    private suspend fun loadMoviesTopRated() {
        when (val response = repository.requestMoviesTopRated()) {
            is ApiResult.Success -> _moviesTopRated.postValue(response.result.results)
            is ApiResult.Error -> _notice.postValue(response.msg)
            ApiResult.Loading -> {
            }
        }
    }

    private suspend fun loadMoviesUpcoming() {
        when (val response = repository.requestMoviesUpcoming()) {
            is ApiResult.Success -> _moviesUpcoming.postValue(response.result.results)
            is ApiResult.Error -> _notice.postValue(response.msg)
            ApiResult.Loading -> {
            }
        }
    }
}