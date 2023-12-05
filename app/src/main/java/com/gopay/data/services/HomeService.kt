package com.gopay.data.services

import com.gopay.data.responses.RepositoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by damai007 on 04/December/2023
 */
interface HomeService {

    @GET("/repositories")
    suspend fun getRepositoryList(
        @Query("since") since: String?
    ): Response<List<RepositoryResponse>>
}