package com.kakapo.rxjavaappexample.model

import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface CryptoCurrencyService {

    @GET("{coin}-usd")
    fun getCoinData(@Query("coin") coin: String): Observable

}