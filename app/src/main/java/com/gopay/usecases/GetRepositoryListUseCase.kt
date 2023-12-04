package com.gopay.usecases

import com.gopay.domain.models.RepositoryModel
import com.gopay.domain.repositories.HomeRepository
import com.gopay.network.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by damai007 on 04/December/2023
 */
class GetRepositoryListUseCase(
    private val homeRepository: HomeRepository
) {

    suspend fun execute(): Flow<Resource<List<RepositoryModel>>> {
        return homeRepository.getRepositoryList()
    }
}