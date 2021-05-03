package com.kakapo.rxjavaappexample.model

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoCurrencyService {

    @GET("{coin}-usd")
    fun getCoinData(@Path("coin") coin: String?): Observable<Cryptos.Crypto>

}