package com.android.fintech_demo.domain.model

import java.io.Serializable

data class TransactionHistoryModel(
    val title: String,
    val iconUrl: String,
    val date: String,
    val amount: String,
): Serializable {
    var currencyAmount = ""

    override fun toString(): String {
        return "$title,$iconUrl,$date,$amount,$currencyAmount"
    }
}