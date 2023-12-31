package com.gopay.base.network

import com.gopay.base.dispatcher.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

/**
 * Created by damai007 on 04/December/2023
 */
abstract class NetworkResource<T>(
    private val dispatcherProvider: CoroutineDispatcherProvider
) {

    fun asFlow(): Flow<Resource<T>> = flow {
        val localCache = withContext(dispatcherProvider.io) {
            localFetch()
        }
        if (localCache == null) {
            val remoteResponse = safeApiCall(dispatcherProvider = dispatcherProvider.io) {
                remoteFetch()
            }
            when (remoteResponse) {
                is Resource.Success -> {
                    remoteResponse.data.first?.let { data ->
                        saveLocal(data = data)
                        emit(Resource.Success(data = data))
                    } ?: run {
                        emit(Resource.Error(errorMessage = remoteResponse.data.second))
                    }
                }
                is Resource.Error -> {
                    emit(remoteResponse)
                }
            }
        } else {
            emit(Resource.Success(data = localCache))
        }
    }

    abstract suspend fun remoteFetch(): Pair<T?, String?>

    open suspend fun localFetch(): T? = null

    open suspend fun saveLocal(data: T) {}

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