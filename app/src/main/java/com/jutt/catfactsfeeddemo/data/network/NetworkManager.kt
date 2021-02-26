package com.jutt.catfactsfeeddemo.data.network

import com.jutt.catfactsfeeddemo.BuildConfig
import com.jutt.catfactsfeeddemo.data.models.AnimalFact
import com.jutt.catfactsfeeddemo.data.repositories.PreferencesRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkManager @Inject constructor(private val catsAPIService: ApiService) {

    fun <T> execute(call: Call<T>, callback: Callback<T>) {
        call.enqueue(callback)
    }

    fun <T> execute(call: Call<T>): Response<T?> {
        return try {
            val response = call.execute()
            if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
//                TODO if user is Logged in Revoke Authentication and LOGOUT
            }
            response
        } catch (ex: Exception) {
            Timber.e(ex)
            when (ex) {
                is ConnectException, is UnknownHostException, is SocketException -> {
                    Response.error(
                        HttpURLConnection.HTTP_UNAVAILABLE,
                        textToResponseBody(text = "{'message': 'Internet not available. Please check your Internet settings and try again.'}")
                    )
                }
                else ->
                    Response.error(
                        HttpURLConnection.HTTP_UNAVAILABLE,
                        textToResponseBody(text = "Server not available")
                    )
            }
        }
    }

    private fun textToRequestBody(text: String): RequestBody {
        return text.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    private fun textToResponseBody(text: String): ResponseBody {
        return text.toResponseBody("text/plain".toMediaTypeOrNull())
    }

    /////////////////////////////////////////////////////////////////

    fun getCatTopFacts(): Call<List<AnimalFact>> = catsAPIService.getCatTopFacts()
}
