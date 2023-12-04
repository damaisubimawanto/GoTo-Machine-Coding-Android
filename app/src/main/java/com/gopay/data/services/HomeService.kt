package com.gopay.data.services

import com.gopay.data.responses.RepositoryResponse
import retrofit2.http.GET

/**
 * Created by damai007 on 04/December/2023
 */
interface HomeService {

    @GET("/ORG/repos")
    fun getRepositoryList(): List<RepositoryResponse>
}