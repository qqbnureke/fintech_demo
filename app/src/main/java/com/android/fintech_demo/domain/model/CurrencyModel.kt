package com.android.fintech_demo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
class CurrencyModel {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var usd: Float = 0.0f
    var gbp: Float = 0.0f
    var euro: Float = 0.0f
    var ruble: Float = 1f

    override fun toString(): String {
        return "USD($usd), GBP($gbp), EUR($euro), RUB($ruble)"
    }
}