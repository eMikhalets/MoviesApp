package com.emikhalets.moviesapp.model.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.emikhalets.moviesapp.model.pojo.ResultMovieList
import kotlinx.coroutines.CoroutineScope

class SearchMoviesDataSourceFactory(
        private val scope: CoroutineScope,
        private val query: String
) : DataSource.Factory<Int, ResultMovieList>() {

    val dataSource = MutableLiveData<SearchMoviesDataSource>()

    override fun create(): DataSource<Int, ResultMovieList> {
        val source = SearchMoviesDataSource(scope, query)
        dataSource.postValue(source)
        return source
    }
}