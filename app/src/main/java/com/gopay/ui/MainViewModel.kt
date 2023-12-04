package com.gopay.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gopay.dispatcher.CoroutineDispatcherProvider
import com.gopay.domain.models.RepositoryModel
import com.gopay.network.Resource
import com.gopay.usecases.GetRepositoryListUseCase
import com.gopay.utils.Event
import kotlinx.coroutines.launch

/**
 * Created by damai007 on 04/December/2023
 */
class MainViewModel(
    private val dispatcher: CoroutineDispatcherProvider,
    private val getRepositoryListUseCase: GetRepositoryListUseCase
) : ViewModel() {

    private val _repositoryListLiveData = MutableLiveData<List<RepositoryModel>>()
    val repositoryListLiveData: LiveData<List<RepositoryModel>> get() = _repositoryListLiveData

    private val _loadingLiveData = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData

    private val _errorLiveData = MutableLiveData<Event<Boolean>>()
    val errorLiveData: LiveData<Event<Boolean>> get() = _errorLiveData

    private var repositoryListPool: MutableList<RepositoryModel> = mutableListOf()

    fun getRepositories() {
        viewModelScope.launch(dispatcher.io) {
            _loadingLiveData.postValue(true)
            getRepositoryListUseCase.execute().collect { resource ->
                _loadingLiveData.postValue(false)
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let {
                            repositoryListPool.clear()
                            repositoryListPool.addAll(it)
                            _repositoryListLiveData.postValue(it)
                        }
                        Event(false).let(_errorLiveData::postValue)
                    }
                    is Resource.Error -> {
                        Event(true).let(_errorLiveData::postValue)
                    }
                }
            }
        }
    }

    fun getRepositoriesByWatcherFilter() {
        repositoryListPool.sortedBy {
            it.watcherCount
        }.let(_repositoryListLiveData::postValue)
    }

    fun getRepositoresByForkFilter() {
        repositoryListPool.sortedBy {
            it.forkCount
        }.let(_repositoryListLiveData::postValue)
    }
}