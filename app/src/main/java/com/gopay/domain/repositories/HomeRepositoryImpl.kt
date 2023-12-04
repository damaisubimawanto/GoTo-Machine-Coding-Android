package com.gopay.domain.repositories

import com.gopay.data.services.HomeService
import com.gopay.domain.models.RepositoryModel
import com.gopay.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by damai007 on 04/December/2023
 */
class HomeRepositoryImpl(
    private val homeService: HomeService
) : HomeRepository {

    override suspend fun getRepositoryList(): Flow<Resource<List<RepositoryModel>>> {
        // TODO: getLocalDatabase()
        return try {
            val response = homeService.getRepositoryList()
            if (response.isSuccessful) {
                val responseListModel = response.body()?.map {
                    it.convertToDataModel()
                }
                // TODO: saveToLocalDatabase()
                flowOf(Resource.Success(responseListModel))
            } else {
                flowOf(Resource.Error(errorMessage = response.errorBody()?.toString()))
            }
        } catch (e: Exception) {
            flowOf(Resource.Error(errorMessage = e.message))
        }
    }
}