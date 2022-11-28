package com.android.fintech_demo.data.repository

import com.android.fintech_demo.data.db.Database
import com.android.fintech_demo.data.model.unwrap
import com.android.fintech_demo.data.network.NetworkApi
import com.android.fintech_demo.domain.Repository
import com.android.fintech_demo.domain.model.CardModel
import com.android.fintech_demo.domain.model.CurrencyModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val networkApi: NetworkApi, private val database: Database
) : Repository {

    override suspend fun getCards(): List<CardModel> {
        return try {
            val cards = networkApi.getCards().unwrap()
            database.getCardsDAO().addCards(cards)
            cards
        } catch (_: Exception) {
            database.getCardsDAO().getCards()
        }
    }

    override suspend fun getCurrencies(): CurrencyModel {
        return try {
            val currencyModel = networkApi.getCurrencies().unwrap()
            database.getCurrencyDAO().addCurrency(currencyModel)
            currencyModel
        } catch (ex: Exception) {
            database.getCurrencyDAO().getCurrency()
        }
    }
}