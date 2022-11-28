package com.android.fintech_demo.data.utils

import androidx.room.TypeConverter
import com.android.fintech_demo.domain.model.CardType
import com.android.fintech_demo.domain.model.TransactionHistoryModel

class ConverterUtils {

    @TypeConverter
    fun fromListOfStrings(value: List<String>?): String? {
        return value?.joinToString(",")
    }

    @TypeConverter
    fun fromStringToList(value: String?): List<String>? {
        return value?.split(",")
    }

    @TypeConverter
    fun fromCardType(value: CardType?): String? {
        return when (value) {
            CardType.Mastercard -> "mastercard"
            CardType.Visa -> "visa"
            CardType.UnionPay -> "unionpay"
            else -> null
        }
    }

    @TypeConverter
    fun fromStringToCardType(value: String?): CardType? {
        return when (value) {
            "mastercard" -> CardType.Mastercard
            "visa" -> CardType.Visa
            "unionpay" -> CardType.UnionPay
            else -> CardType.Unknown
        }
    }

    @TypeConverter
    fun fromHistoryTransaction(value: List<TransactionHistoryModel>?): String? {
        return value?.joinToString("|||")
    }

    @TypeConverter
    fun fromStringToTransaction(value: String?): List<TransactionHistoryModel>? {
        try {
            val list = arrayListOf<TransactionHistoryModel>()
            value?.split("|||")?.forEach {
                val fields = it.split(",")
                list.add(TransactionHistoryModel(
                    title = fields[0],
                    iconUrl = fields[1],
                    date = fields[2],
                    amount = fields[3],
                ).apply {
                    currencyAmount = fields[4]
                })
            }
            return list
        } catch (_: Exception) {
            return emptyList()
        }
    }
}