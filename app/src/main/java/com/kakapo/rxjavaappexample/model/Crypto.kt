package com.kakapo.rxjavaappexample.model

import com.google.gson.annotations.SerializedName

data class Crypto(
    @SerializedName("ticker")
    val ticker: String = ""
)