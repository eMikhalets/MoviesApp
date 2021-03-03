package com.emikhalets.moviesapp.model.repository

import androidx.paging.PageKeyedDataSource
import com.emikhalets.moviesapp.model.network.ApiFactory
import com.emikhalets.moviesapp.model.pojo.ResultMovieList
import com.emikhalets.moviesapp.utils.MovieQueries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MoviesDataSource(
    private val scope: CoroutineScope,
    private val query: MovieQueries
) : PageKeyedDataSource<Int, ResultMovieList>() {

    private val api = ApiFactory.get()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultMovieList>
    ) {
        scope.launch {
            when (query) {
                MovieQueries.POPULAR -> {
                    val response = api.moviePopular(1)
                    callback.onResult(response.results, null, 2)
                }
                MovieQueries.NOW_PLAYING -> {
                    val response = api.movieNowPlaying(1)
                    callback.onResult(response.results, null, 2)
                }
                MovieQueries.TOP_RATED -> {
                    val response = api.movieTopRated(1)
                    callback.onResult(response.results, null, 2)
                }
                MovieQueries.UPCOMING -> {
                    val response = api.movieUpcoming(1)
                    callback.onResult(response.results, null, 2)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovieList>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultMovieList>) {
        scope.launch {
            val page = params.key
            when (query) {
                MovieQueries.POPULAR -> {
                    val response = api.moviePopular(1)
                    callback.onResult(response.results, page + 1)
                }
                MovieQueries.NOW_PLAYING -> {
                    val response = api.movieNowPlaying(1)
                    callback.onResult(response.results, page + 1)
                }
                MovieQueries.TOP_RATED -> {
                    val response = api.movieTopRated(1)
                    callback.onResult(response.results, page + 1)
                }
                MovieQueries.UPCOMING -> {
                    val response = api.movieUpcoming(1)
                    callback.onResult(response.results, page + 1)
                }
            }
        }
    }
}