package com.android.fintech_demo.data.model

import android.util.Log
import com.android.fintech_demo.domain.model.CurrencyModel
import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class CurrencyResponseDto(
    @SerializedName("Valute")
    val valute: HashMap<String, CurrencyDto>
)

data class CurrencyDto(
    @SerializedName("CharCode")
    val code: String,
    @SerializedName("Value")
    val value: Float
)

fun Response<CurrencyResponseDto>.unwrap(): CurrencyModel {
    if (isSuccessful && body() != null) {
        val responseWrapper = body() as CurrencyResponseDto
        val currencyModel = CurrencyModel()
        Log.i("MainViewModel", "unwrap: $responseWrapper")
        responseWrapper.valute.forEach { (code, value) ->
            when(code) {
                "USD" -> currencyModel.usd = value.value
                "EUR" -> currencyModel.euro = value.value
                "RUB" -> currencyModel.ruble = value.value
                "GBP" -> currencyModel.gbp = value.value
            }
        }
        return currencyModel
    } else {
        throw NetworkException(this.code(), this.errorBody().toString())
    }
}