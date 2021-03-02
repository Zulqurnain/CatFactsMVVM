package com.jutt.catfactsfeeddemo.extensions

import android.util.Log
import org.json.JSONObject
import retrofit2.Response

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
                Log.e("retrofit","Unable to parse error response",ex)
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
        Log.e("retrofit","Unable to parse error body",ex)
        null
    }
