package com.emikhalets.moviesapp.model.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.emikhalets.moviesapp.model.pojo.ResultPopArtist
import kotlinx.coroutines.CoroutineScope


class PopPersonDataSourceFactory(
    private val scope: CoroutineScope
) : DataSource.Factory<Int, ResultPopArtist>() {

    val dataSource = MutableLiveData<PopPersonDataSource>()

    override fun create(): DataSource<Int, ResultPopArtist> {
        val source = PopPersonDataSource(scope)
        dataSource.postValue(source)
        return source
    }
}