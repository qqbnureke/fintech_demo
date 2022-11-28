package com.android.fintech_demo.domain

import com.android.fintech_demo.domain.model.CardModel
import com.android.fintech_demo.domain.model.CurrencyModel

interface Repository {
    suspend fun getCards(): List<CardModel>

    suspend fun getCurrencies(): CurrencyModel
}