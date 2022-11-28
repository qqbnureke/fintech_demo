package com.android.fintech_demo.data.network

import com.android.fintech_demo.data.model.CurrencyResponseDto
import com.android.fintech_demo.data.model.ResponseWrapper
import retrofit2.Response
import retrofit2.http.GET

interface NetworkApi {
    @GET("test/android/v1/users.json")
    suspend fun getCards(): Response<ResponseWrapper>

    @GET("https://www.cbr-xml-daily.ru/daily_json.js")
    suspend fun getCurrencies(): Response<CurrencyResponseDto>
}