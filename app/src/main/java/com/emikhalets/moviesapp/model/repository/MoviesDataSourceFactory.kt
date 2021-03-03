package com.emikhalets.moviesapp.model.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.emikhalets.moviesapp.model.pojo.ResultMovieList
import com.emikhalets.moviesapp.utils.MovieQueries
import kotlinx.coroutines.CoroutineScope

class MoviesDataSourceFactory(
    private val scope: CoroutineScope,
    private val query: MovieQueries
) : DataSource.Factory<Int, ResultMovieList>() {

    val dataSource = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, ResultMovieList> {
        val source = MoviesDataSource(scope, query)
        dataSource.postValue(source)
        return source
    }
}