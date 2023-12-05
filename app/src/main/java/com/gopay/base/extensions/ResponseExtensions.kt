package com.gopay.base.extensions

import retrofit2.Response

/**
 * Created by damai007 on 05/December/2023
 */

fun <T> Response<T>.errorMessage() = this.errorBody()?.toString()