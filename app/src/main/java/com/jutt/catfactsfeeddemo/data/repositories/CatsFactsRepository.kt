package com.jutt.catfactsfeeddemo.data.repositories

import com.jutt.catfactsfeeddemo.data.models.AnimalFact
import com.jutt.catfactsfeeddemo.data.network.NetworkManager
import com.jutt.catfactsfeeddemo.data.network.NetworkResponse
import com.jutt.catfactsfeeddemo.data.network.toNetworkResponse
import com.jutt.catfactsfeeddemo.di.NamedHilts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CatsFactsRepository @Inject constructor(
    private val networkManager: NetworkManager,
    @Named(NamedHilts.REPOSITORY_DISPATCHER)
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun fetchCatFactsFromAPI(): NetworkResponse<List<AnimalFact>> =
        withContext(dispatcher) {
            val response = networkManager.execute(
                networkManager.getCatTopFacts()
            )
            val body = response.body()

            return@withContext response.toNetworkResponse {
                var returnList = emptyList<AnimalFact>()
                if (isSuccessful){
                    returnList = body?.toList() ?: emptyList()
                }
                returnList
            }
        }

}