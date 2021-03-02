package com.jutt.catfactsfeeddemo.data.network

import android.util.Log
import com.jutt.catfactsfeeddemo.BuildConfig
import com.jutt.catfactsfeeddemo.data.models.AnimalFact
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
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkManager @Inject constructor(private val catsAPIService: ApiService) {

    fun <T> execute(call: Call<T>): Response<T?> {
        return try {
            val response = call.execute()
            if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
//                TODO if user is Logged in Revoke Authentication and LOGOUT
                Log.e("executeRetrofit","AUTH issue")
            }
            response
        } catch (ex: Exception) {
            Log.e("networkManager", "execute: ", ex)
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

    private fun textToResponseBody(text: String): ResponseBody {
        return text.toResponseBody("text/plain".toMediaTypeOrNull())
    }

    /////////////////////////////////////////////////////////////////

    fun getCatTopFacts(): Call<List<AnimalFact>> = catsAPIService.getCatTopFacts()
}
