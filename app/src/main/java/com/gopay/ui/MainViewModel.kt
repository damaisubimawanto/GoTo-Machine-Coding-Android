package com.gopay.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gopay.dispatcher.CoroutineDispatcherProvider
import com.gopay.domain.models.RepositoryModel
import com.gopay.domain.models.SortMenuType
import com.gopay.network.Resource
import com.gopay.usecases.GetRepositoryListUseCase
import com.gopay.utils.Event
import kotlinx.coroutines.delay
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

    private val _sortMenuLiveData = MutableLiveData<SortMenuType>(SortMenuType.ClearSort)
    val sortMenuLiveData: LiveData<SortMenuType> get() = _sortMenuLiveData

    private var repositoryListPool: MutableList<RepositoryModel> = mutableListOf()
    var isReset = false

    fun getRepositories() {
        viewModelScope.launch(dispatcher.io) {
            _loadingLiveData.postValue(true)
            getRepositoryListUseCase.execute().collect { resource ->
                _loadingLiveData.postValue(false)
                when (resource) {
                    is Resource.Success -> {
                        resource.data.let {
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
        isReset = true
        repositoryListPool.sortedByDescending {
            it.stars
        }.let(_repositoryListLiveData::postValue)
        setSortMenu(type = SortMenuType.SortByWatcher)
    }

    fun getRepositoresByForkFilter() {
        isReset = true
        repositoryListPool.sortedByDescending {
            it.forks
        }.let(_repositoryListLiveData::postValue)
        setSortMenu(type = SortMenuType.SortByFork)
    }

    fun getDefaultRepositories() {
        isReset = true
        repositoryListPool.let(_repositoryListLiveData::postValue)
        setSortMenu(type = SortMenuType.ClearSort)
    }

    private fun setSortMenu(type: SortMenuType) {
        viewModelScope.launch {
            delay(1_000L)
            type.let(_sortMenuLiveData::postValue)
        }
    }
}