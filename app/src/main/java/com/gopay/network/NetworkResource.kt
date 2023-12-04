package com.gopay.network

import com.gopay.dispatcher.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

/**
 * Created by damai007 on 04/December/2023
 */
abstract class NetworkResource<T>(
    private val dispatcherProvider: CoroutineDispatcherProvider
) {

    fun asFlow(): Flow<Resource<T>> = flow {

        suspend fun fetchFromRemote(): Flow<Resource<T>> {
            val remoteResponse = safeApiCall(dispatcherProvider = dispatcherProvider.io) {
                remoteFetch()
            }
            when (remoteResponse) {
                is Resource.Success -> {
                    remoteResponse.data?.let { data ->
                        saveLocal(data = data)
                    }
                }
                is Resource.Error -> Unit
            }
            return flowOf(remoteResponse)
        }

        if (shouldFetchFromRemote()) {
            fetchFromRemote()
        } else {
            /* Get from cache. */
            val localCache = withContext(dispatcherProvider.io) {
                localFetch()
            }
            if (localCache == null) {
                fetchFromRemote()
            } else {
                emit(Resource.Success(data = localCache))
            }
        }
    }

    abstract suspend fun remoteFetch(): T

    open suspend fun localFetch(): T? = null

    open suspend fun saveLocal(data: T) {}

    open fun shouldFetchFromRemote() = true

}

internal suspend fun <T> safeApiCall(
    dispatcherProvider: CoroutineDispatcher,
    apiCall: suspend () -> T
): Resource<T> {
    return withContext(dispatcherProvider) {
        try {
            val call = apiCall.invoke()
            Resource.Success(data = call)
        } catch (e: Throwable) {
            e.printStackTrace()
            Resource.Error(errorMessage = e.message)
        }
    }
}