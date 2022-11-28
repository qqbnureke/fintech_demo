package com.android.fintech_demo.data.model

import com.android.fintech_demo.domain.model.CardModel
import com.android.fintech_demo.domain.model.CardType
import com.google.gson.annotations.SerializedName

data class CardDto(
    @SerializedName("card_number") val cardNumber: String,
    val type: String,
    @SerializedName("cardholder_name") val cardholderName: String,
    @SerializedName("valid") val validDate: String,
    val balance: Float,
    @SerializedName("transaction_history") val transactionHistory: List<TransactionHistoryDto>,
)

fun CardDto.toModel(): CardModel {
    return CardModel(
        this.cardNumber,
        when (type) {
            "mastercard" -> CardType.Mastercard
            "visa" -> CardType.Visa
            "unionpay" -> CardType.UnionPay
            else -> CardType.Unknown
        },
        this.cardholderName,
        this.validDate,
        this.balance,
        this.transactionHistory.map(TransactionHistoryDto::toModel)
    )
}