package com.emikhalets.moviesapp.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.moviesapp.model.network.ApiFactory
import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.pojo.ResponsePersonId
import com.emikhalets.moviesapp.model.repository.PersonDetailsRepository
import kotlinx.coroutines.launch

class PersonDetailsViewModel : ViewModel() {

    private val repository = PersonDetailsRepository(ApiFactory.get())

    private val _person = MutableLiveData<ResponsePersonId>()
    val person get(): LiveData<ResponsePersonId> = _person

    private val _uiVisibility = MutableLiveData<Boolean>()
    val uiVisibility get(): LiveData<Boolean> = _uiVisibility

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    var id = -1

    @SuppressLint("NullSafeMutableLiveData")
    fun loadPersonData(personId: Int) {
        viewModelScope.launch {
            _uiVisibility.postValue(false)
            when (val response = repository.requestPersonById(personId)) {
                is ApiResult.Success -> {
                    _person.postValue(response.result)
                    _uiVisibility.postValue(true)
                }
                is ApiResult.Error -> _notice.postValue(response.msg)
                ApiResult.Loading -> {
                }
            }
        }
    }

    fun getPerson(): ResponsePersonId? {
        return _person.value
    }
}