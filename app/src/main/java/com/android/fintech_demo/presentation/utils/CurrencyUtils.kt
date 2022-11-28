package com.android.fintech_demo.presentation.utils

import com.android.fintech_demo.domain.model.CardModel
import com.android.fintech_demo.domain.model.CurrencyModel

enum class Currency(val sign: String) {
    GBP("£"), RUB("₽"), EUR("€")
}

class CurrencyUtils {
    companion object {
        var cardList: List<CardModel>? = null
        var currencyModel: CurrencyModel? = null

        fun cardCurrency(current: Currency = Currency.GBP, ind: Float? = null): Float? {
            val currency = currencyModel
            val cards = cardList
            if (currency == null || cards == null) return null

            val card = cards.first()
            val rub = (ind ?: card.balance) * currency.usd
            val result = when (current) {
                Currency.GBP -> rub / currency.gbp
                Currency.EUR -> rub / currency.euro
                Currency.RUB -> rub / currency.ruble
            }
            return result
        }
    }
}