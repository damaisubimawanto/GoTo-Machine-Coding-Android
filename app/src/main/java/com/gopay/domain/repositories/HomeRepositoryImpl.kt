package com.gopay.domain.repositories

import com.gopay.data.services.HomeService
import com.gopay.domain.models.RepositoryModel
import com.gopay.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by damai007 on 04/December/2023
 */
class HomeRepositoryImpl(
    private val homeService: HomeService
) : HomeRepository {

    override suspend fun getRepositoryList(): Flow<Resource<List<RepositoryModel>>> {
        // TODO: getLocalDatabase()
        val response = try {
            Resource.Success(
                homeService.getRepositoryList().map {
                    it.map()
                }
            )
        } catch (e: Exception) {
            Resource.Error(errorMessage = e.message)
        }
        // TODO: saveToLocalDatabase()
        return flowOf(response)
    }
}