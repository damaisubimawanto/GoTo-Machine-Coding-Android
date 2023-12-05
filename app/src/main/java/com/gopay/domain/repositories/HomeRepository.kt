package com.gopay.domain.repositories

import com.gopay.domain.models.RepositoryModel
import com.gopay.base.network.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by damai007 on 04/December/2023
 */
interface HomeRepository {
    suspend fun getRepositoryList(): Flow<Resource<List<RepositoryModel>>>
}