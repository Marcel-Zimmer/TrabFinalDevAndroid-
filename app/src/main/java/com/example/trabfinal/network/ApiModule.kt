package com.example.trabfinal.network

import androidx.collection.ArraySet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiModule {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://economia.awesomeapi.com.br/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val interaceApi: InterfaceApi = retrofit.create(InterfaceApi::class.java);

    suspend fun getCurrentExchangeRate(coinOrigin: String, coinDestination: String): Double {
        var coinBid: Double = 0.0
        val concat = "$coinOrigin-$coinDestination"
        try {
            val response = withContext(Dispatchers.IO) {
                interaceApi.getBlockchainTicker(concat)
            }
            val key = response.keys.first()
            coinBid = response[key]?.bid?.toString()?.toDouble() ?: 0.0
        } catch (e: Exception) {
            println("Erro ao chamar a API: ${e.localizedMessage}")
        }
        return coinBid
    }


}



