package com.gopay.domain.repositories

import com.gopay.data.services.HomeService
import com.gopay.dispatcher.CoroutineDispatcherProvider
import com.gopay.domain.models.RepositoryModel
import com.gopay.network.NetworkResource
import com.gopay.network.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by damai007 on 04/December/2023
 */
class HomeRepositoryImpl(
    private val homeService: HomeService,
    private val dispatcherProvider: CoroutineDispatcherProvider
) : HomeRepository {

    override suspend fun getRepositoryList(): Flow<Resource<List<RepositoryModel>>> {
        return object : NetworkResource<List<RepositoryModel>>(
            dispatcherProvider = dispatcherProvider
        ) {
            override suspend fun remoteFetch(): List<RepositoryModel> {
                val response = homeService.getRepositoryList()
                return if (response.isSuccessful) {
                    response.body()?.map {
                        it.convertToDataModel()
                    } ?: listOf()
                } else listOf()
            }

            override suspend fun localFetch(): List<RepositoryModel>? {
                return super.localFetch()
            }

            override suspend fun saveLocal(data: List<RepositoryModel>) {
                super.saveLocal(data)
            }
        }.asFlow()
    }
}