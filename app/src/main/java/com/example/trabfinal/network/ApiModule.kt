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
        var concat = "$coinOrigin-$coinDestination"

        if(concat == "BTC-ETH" || concat == "ETH-BTC") {
            var bitcoinBid = 0.0
            var etherBid = 0.0
            try {
                val responseBTC = withContext(Dispatchers.IO) {
                    interaceApi.getBlockchainTicker("BTC-USD")
                }
                val responseETH = withContext(Dispatchers.IO) {
                    interaceApi.getBlockchainTicker("ETH-USD")
                }
                val btcKey = responseBTC.keys.first()
                bitcoinBid = responseBTC[btcKey]?.bid?.toString()?.toDouble() ?: 0.0

                val ethKey = responseETH.keys.first()
                etherBid = responseETH[ethKey]?.bid?.toString()?.toDouble() ?: 0.0
            } catch (e: Exception) {
                throw Exception("Erro ao processar a conversão de moedas: ${e.localizedMessage}", e)
            }
            return if(concat == "BTC-ETH")
                 bitcoinBid/etherBid
            else
                 etherBid/bitcoinBid

        }else if(coinDestination == "BTC"){
            concat = "$coinDestination-$coinOrigin"
            try {
                val response = withContext(Dispatchers.IO) {
                    interaceApi.getBlockchainTicker(concat)
                }
                val key = response.keys.first()
                coinBid = response[key]?.bid?.toString()?.toDouble() ?: 0.0
            } catch (e: Exception) {
                println("Erro ao chamar a API: ${e.localizedMessage}")
            }
            return 1/coinBid

        }
        else if(coinDestination == "ETH"){
            concat = "$coinDestination-$coinOrigin"
            try {
                val response = withContext(Dispatchers.IO) {
                    interaceApi.getBlockchainTicker(concat)
                }
                val key = response.keys.first()
                coinBid = response[key]?.bid?.toString()?.toDouble() ?: 0.0
            } catch (e: Exception) {
                println("Erro ao chamar a API: ${e.localizedMessage}")
            }
            return 1/coinBid

        }
        else{
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


}



