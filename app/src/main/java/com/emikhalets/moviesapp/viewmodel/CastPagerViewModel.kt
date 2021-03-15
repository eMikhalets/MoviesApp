package com.emikhalets.moviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.moviesapp.model.network.ApiFactory
import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.pojo.Cast
import com.emikhalets.moviesapp.model.pojo.Crew
import com.emikhalets.moviesapp.model.repository.CastPagerRepository
import kotlinx.coroutines.launch

class CastPagerViewModel : ViewModel() {

    private val repository = CastPagerRepository(ApiFactory.get())

    private val _cast = MutableLiveData<List<Cast>>()
    val cast get(): LiveData<List<Cast>> = _cast

    private val _crew = MutableLiveData<List<Crew>>()
    val crew get(): LiveData<List<Crew>> = _crew

    private val _uiVisibility = MutableLiveData<Boolean>()
    val uiVisibility get(): LiveData<Boolean> = _uiVisibility

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    fun loadCastAndCrew(movieId: Int) {
        viewModelScope.launch {
            _uiVisibility.postValue(false)
            when (val response = repository.requestMovieCredits(movieId)) {
                is ApiResult.Success -> {
                    _cast.postValue(response.result.cast)
                    _crew.postValue(response.result.crew)
                    _uiVisibility.postValue(true)
                }
                is ApiResult.Error -> _notice.postValue(response.msg)
            }
        }
    }
}