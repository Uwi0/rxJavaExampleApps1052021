package com.kakapo.rxjavaappexample.model

import com.google.gson.annotations.SerializedName

data class Crypto(
    @SerializedName("ticker")
    var ticker: Ticker = Ticker(),
    @SerializedName("timestamp")
    var timeStamp: Int = 0,
    @SerializedName("success")
    var success: Boolean = false,
    @SerializedName("error")
    var error: String? = null

){
    data class Ticker(
        @SerializedName("name")
        val base: String? = null,
        @SerializedName("target")
        val target: String? = null,
        @SerializedName("price")
        val price: String? = null,
        @SerializedName("volume")
        val volume: String? = null,
        @SerializedName("change")
        val change: String? = null,
        @SerializedName("market")
        val market: List<Market>? = null
    )

    data class Market(
        @SerializedName("market")
        val market: String? = null,
        @SerializedName("price")
        val price: String? = null,
        @SerializedName("volume")
        val volume: Float? = 0.0f,

        val coinName: String? = null
    )
}
