package com.emikhalets.moviesapp.model.repository

import androidx.paging.PageKeyedDataSource
import com.emikhalets.moviesapp.model.network.ApiFactory
import com.emikhalets.moviesapp.model.pojo.ResultMovieList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SearchMoviesDataSource(
        private val scope: CoroutineScope,
        private val query: String
) : PageKeyedDataSource<Int, ResultMovieList>() {

    private val api = ApiFactory.get()

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, ResultMovieList>
    ) {
        scope.launch {
            val response = api.searchMovies(query, 1)
            callback.onResult(response.results, null, 2)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovieList>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovieList>) {
        scope.launch {
            val page = params.key
            val response = api.searchMovies(query, page)
            callback.onResult(response.results, page + 1)
        }
    }
}