package com.kakapo.rxjavaappexample.model

object Cryptos {
   data class Crypto(
    val error: String,
    val success: Boolean,
    val ticker: Ticker,
    val timestamp: Int
)

data class Ticker(
    val base: String,
    val change: String,
    var markets: List<Market>,
    val price: String,
    val target: String,
    val volume: String
)

data class  Market(
    var market: String,
    val price: String,
    val volume: Double
)
}