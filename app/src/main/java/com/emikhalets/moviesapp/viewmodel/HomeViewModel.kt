package com.emikhalets.moviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.emikhalets.moviesapp.model.network.ApiFactory
import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.pojo.ResultMovieList
import com.emikhalets.moviesapp.model.pojo.ResultPopArtist
import com.emikhalets.moviesapp.model.repository.HomeRepository
import com.emikhalets.moviesapp.model.repository.SearchMoviesDataSourceFactory
import com.emikhalets.moviesapp.utils.HomeState
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = HomeRepository(ApiFactory.get())

    private val _popArtists = MutableLiveData<List<ResultPopArtist>>()
    val popArtists get(): LiveData<List<ResultPopArtist>> = _popArtists

    private val _moviesPopular = MutableLiveData<List<ResultMovieList>>()
    val moviesPopular get(): LiveData<List<ResultMovieList>> = _moviesPopular

    private val _moviesNowPlaying = MutableLiveData<List<ResultMovieList>>()
    val moviesPlaying get(): LiveData<List<ResultMovieList>> = _moviesNowPlaying

    private val _moviesTopRated = MutableLiveData<List<ResultMovieList>>()
    val moviesTopRated get(): LiveData<List<ResultMovieList>> = _moviesTopRated

    private val _moviesUpcoming = MutableLiveData<List<ResultMovieList>>()
    val moviesUpcoming get(): LiveData<List<ResultMovieList>> = _moviesUpcoming

    private val _visibility = MutableLiveData<HomeState>()
    val visibility get(): LiveData<HomeState> = _visibility

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    private var dataCounter = 0
    var isNightMode = false
        get() {
            field = !field
            return field
        }

    var search: LiveData<PagedList<ResultMovieList>>? = null

    fun initSearchPager(query: String) {
        val dataSource = SearchMoviesDataSourceFactory(viewModelScope, query)
        search = LivePagedListBuilder(dataSource, 20).build()
        dataSource.dataSource.value?.invalidate()
    }

    fun updateVisibility(state: HomeState) {
        _visibility.value = state
    }

    fun loadData() {
        viewModelScope.launch {
            _visibility.postValue(HomeState.LOADING)
            loadPopArtists()
            loadMoviesPopular()
            loadMoviesNowPlaying()
            loadMoviesTopRated()
            loadMoviesUpcoming()
        }
    }

    private suspend fun loadPopArtists() {
        when (val response = repository.requestPersonPopular()) {
            is ApiResult.Success -> {
                _popArtists.postValue(response.result.results)
                if (++dataCounter >= 5) _visibility.postValue(HomeState.DEFAULT)
            }
            is ApiResult.Error -> _notice.postValue(response.msg)
        }
    }

    private suspend fun loadMoviesPopular() {
        when (val response = repository.requestMoviesPopular()) {
            is ApiResult.Success -> {
                _moviesPopular.postValue(response.result.results)
                if (++dataCounter >= 5) _visibility.postValue(HomeState.DEFAULT)
            }
            is ApiResult.Error -> _notice.postValue(response.msg)
        }
    }

    private suspend fun loadMoviesNowPlaying() {
        when (val response = repository.requestMoviesNowPlaying()) {
            is ApiResult.Success -> {
                _moviesNowPlaying.postValue(response.result.results)
                if (++dataCounter >= 5) _visibility.postValue(HomeState.DEFAULT)
            }
            is ApiResult.Error -> _notice.postValue(response.msg)
        }
    }

    private suspend fun loadMoviesTopRated() {
        when (val response = repository.requestMoviesTopRated()) {
            is ApiResult.Success -> {
                _moviesTopRated.postValue(response.result.results)
                if (++dataCounter >= 5) _visibility.postValue(HomeState.DEFAULT)
            }
            is ApiResult.Error -> _notice.postValue(response.msg)
        }
    }

    private suspend fun loadMoviesUpcoming() {
        when (val response = repository.requestMoviesUpcoming()) {
            is ApiResult.Success -> {
                _moviesUpcoming.postValue(response.result.results)
                if (++dataCounter >= 5) _visibility.postValue(HomeState.DEFAULT)
            }
            is ApiResult.Error -> _notice.postValue(response.msg)
        }
    }
}