package com.gopay.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gopay.dispatcher.CoroutineDispatcherProvider
import com.gopay.domain.models.RepositoryModel
import com.gopay.network.Resource
import com.gopay.usecases.GetRepositoryListUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by damai007 on 04/December/2023
 */
class MainViewModel(
    private val dispatcher: CoroutineDispatcherProvider,
    private val getRepositoryListUseCase: GetRepositoryListUseCase
) : ViewModel() {

    private val _repositoryListLiveData: MutableLiveData<List<RepositoryModel>> = MutableLiveData()
    val repositoryListLiveData: LiveData<List<RepositoryModel>> get() = _repositoryListLiveData

    private val _errorLiveData = MutableLiveData(false)
    val errorLiveData: LiveData<Boolean> get() = _errorLiveData

    fun getRepositories() {
        viewModelScope.launch(dispatcher.io) {
            getRepositoryListUseCase.execute().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { data ->
                            _repositoryListLiveData.postValue(data)
                        }
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }
}