package com.jutt.catfactsfeeddemo.data.network

import com.jutt.catfactsfeeddemo.extensions.string
import retrofit2.Response

data class NetworkResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null,
    val responseCode: Int = 0
) { companion object }

inline fun <reified T, reified E> Response<T>.toNetworkResponse(bodyPredicate: Response<T>.() -> E?) =
    NetworkResponse(
        success = isSuccessful,
        message = if(isSuccessful.not()) this.string else "",
        data = bodyPredicate(),
        responseCode = this.code()
    )
