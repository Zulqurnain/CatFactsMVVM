package com.jutt.catfactsfeeddemo.data.network

import com.jutt.catfactsfeeddemo.data.models.AnimalFact
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/facts")
    fun getCatTopFacts(): Call<List<AnimalFact>>

//    @POST("/signup")
//    fun signUp(
//        @HeaderMap headers: Map<String, String>,
//        @Body body: Map<String, @JvmSuppressWildcards Any?>
//    ): Call<SignUpResponse>
}
