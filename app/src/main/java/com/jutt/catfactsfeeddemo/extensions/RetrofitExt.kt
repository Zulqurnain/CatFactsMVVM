package com.jutt.catfactsfeeddemo.extensions

import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber

val <T> Response<T>.string: String?
    get() {
        if (isSuccessful) {
            return message()
        } else {
            try {
                val errors = JSONObject(errorBody()?.string() ?: "{}")
                val message: String? = errors.optString("message")
                if (message.isNullOrBlank()) {
                    val errorMessage: String? = errors.optMessageFromArray
                    if (errorMessage.isNullOrBlank()) {
                        return errors.optJSONObject("errors")?.optMessageFromArray
                    }
                    return errorMessage
                }
                return message
            } catch (ex: Exception) {
                Timber.e(ex, "Unable to parse error response")
            }
        }
        return null
    }

private val JSONObject.optMessageFromArray: String?
    get() = try {
        keys().asSequence()
            .map { optJSONArray(it)?.join("\n") }
            .filterNotNull()
            .joinToString(separator = "\n") { it.trim('"') }
    } catch (ex: Exception) {
        Timber.e(ex, "Unable to parse error body")
        null
    }
