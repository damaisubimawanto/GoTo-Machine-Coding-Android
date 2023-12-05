package com.gopay.network

/**
 * Created by damai007 on 04/December/2023
 */
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()

    data class Error(val errorMessage: String?) : Resource<Nothing>()
}
