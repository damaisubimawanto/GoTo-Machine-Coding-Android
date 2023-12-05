package com.gopay.domain.repositories

import com.gopay.data.services.HomeService
import com.gopay.base.dispatcher.CoroutineDispatcherProvider
import com.gopay.domain.models.RepositoryModel
import com.gopay.base.extensions.errorMessage
import com.gopay.base.network.NetworkResource
import com.gopay.base.network.Resource
import com.gopay.base.utils.Constants.QUERY_SINCE_DAILY
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
            override suspend fun remoteFetch(): Pair<List<RepositoryModel>?, String?> {
                val response = homeService.getRepositoryList(
                    since = QUERY_SINCE_DAILY
                )
                return if (response.isSuccessful) {
                    val responseModel = response.body()?.map {
                        it.convertToDataModel()
                    } ?: listOf()
                    Pair(responseModel, null)
                } else {
                    /* If response is not successfull, then give the error message. */
                    Pair(null, response.errorMessage())
                }
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