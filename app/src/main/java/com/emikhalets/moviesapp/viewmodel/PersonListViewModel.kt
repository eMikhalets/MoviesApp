package com.emikhalets.moviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.emikhalets.moviesapp.model.pojo.ResultPopArtist
import com.emikhalets.moviesapp.model.repository.PopPersonDataSourceFactory

class PersonListViewModel : ViewModel() {

    private val dataSource = PopPersonDataSourceFactory(viewModelScope)
    val person: LiveData<PagedList<ResultPopArtist>> = dataSource.toLiveData(20)

    fun initPager() {
        dataSource.dataSource.value?.invalidate()
    }
}