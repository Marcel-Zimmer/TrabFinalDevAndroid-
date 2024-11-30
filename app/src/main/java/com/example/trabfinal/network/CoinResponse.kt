package com.example.trabfinal.network

data class CoinResponse(
    val USDBRL : CoinData,
    val EURBRL : CoinData,
    val BTCBRL : CoinData,
    val ETHBRL : CoinData,

    val EURUSD : CoinData,
    val BTCUSD : CoinData,
    val ETHUSD : CoinData,
    val BRLUSD : CoinData,

    val USDEUR : CoinData,
    val BTCEUR : CoinData,
    val ETHEUR : CoinData,
    val BRLEUR : CoinData,

    val USDETH : CoinData,
    val BTCETH : CoinData,
    val EURETH : CoinData,
    val BRLETH : CoinData,

    val USDBTC : CoinData,
    val ETHBTC : CoinData,
    val EURBTC : CoinData,
    val BRLBTC : CoinData,


)

data class CoinData(
    val code: String,
    val codein: String,
    val name: String,
    val high: String,
    val low: String,
    val varBid: String,
    val pctChange: String,
    val bid: String,
    val ask: String,
    val timestamp: String,
    val create_date: String
)
