package com.example.trabfinal.network

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceApi {
        @GET("json/last/{coin}")
        suspend fun getBlockchainTicker(@Path("coin") coin: String): CoinResponse

}