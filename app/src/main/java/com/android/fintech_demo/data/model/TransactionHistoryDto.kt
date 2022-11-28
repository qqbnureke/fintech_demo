package com.android.fintech_demo.data.model

import com.android.fintech_demo.domain.model.TransactionHistoryModel
import com.google.gson.annotations.SerializedName

data class TransactionHistoryDto(
    val title: String,
    @SerializedName("icon_url") val iconUrl: String,
    val date: String,
    val amount: String,
)

fun TransactionHistoryDto.toModel(): TransactionHistoryModel {
    return TransactionHistoryModel(this.title, this.iconUrl, this.date, this.amount)
}