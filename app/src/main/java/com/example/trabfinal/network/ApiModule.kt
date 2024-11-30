package com.example.trabfinal.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiModule {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://economia.awesomeapi.com.br/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val interaceApi: InterfaceApi = retrofit.create(InterfaceApi::class.java);

    fun getCurrentExchangeRate(coinOrigin : String, coinDestination : String) {
        println("entrou")
        val concat = "$coinOrigin$coinDestination"
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = interaceApi.getBlockchainTicker(patch)
                println(response.concat.bid)
            } catch (e: Exception) {
                println("Erro ao chamar a API: ${e.localizedMessage}")
            }
        }
    }

}



