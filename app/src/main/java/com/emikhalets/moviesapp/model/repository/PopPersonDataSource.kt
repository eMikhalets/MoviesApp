package com.emikhalets.moviesapp.model.repository

import androidx.paging.PageKeyedDataSource
import com.emikhalets.moviesapp.model.network.ApiFactory
import com.emikhalets.moviesapp.model.pojo.ResultPopArtist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PopPersonDataSource(
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, ResultPopArtist>() {

    private val api = ApiFactory.get()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultPopArtist>
    ) {
        scope.launch {
            val response = api.personPopular(1)
            callback.onResult(response.results, null, 2)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultPopArtist>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultPopArtist>) {
        scope.launch {
            val page = params.key
            val response = api.personPopular(page)
            callback.onResult(response.results, page + 1)
        }
    }
}