package com.emikhalets.moviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.emikhalets.moviesapp.model.pojo.ResultMovieList
import com.emikhalets.moviesapp.model.repository.MoviesDataSourceFactory
import com.emikhalets.moviesapp.utils.MovieQueries

class MoviesPagerViewModel : ViewModel() {

    private var dataSource: MoviesDataSourceFactory? = null
    var movies: LiveData<PagedList<ResultMovieList>>? = null

    fun initPager(query: MovieQueries) {
        dataSource = MoviesDataSourceFactory(viewModelScope, query)
        movies = dataSource?.toLiveData(20)
        dataSource?.dataSource?.value?.invalidate()
    }
}