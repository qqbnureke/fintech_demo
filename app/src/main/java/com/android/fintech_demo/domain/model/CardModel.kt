package com.android.fintech_demo.domain.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.android.fintech_demo.R
import java.io.Serializable

@Entity(tableName = "cards_table")
data class CardModel(
    @PrimaryKey(autoGenerate = false)
    val cardNumber: String,
    val type: CardType,
    val cardholderName: String,
    val validDate: String,
    val balance: Float,
    var transactionHistory: List<TransactionHistoryModel>
) : Serializable {
    @Ignore
    val cardTypeIcon: Int = type.getIconFromType()
    @Ignore
    var isSelected: Boolean = false
}

@DrawableRes
private fun CardType.getIconFromType(): Int {
    return when (this) {
        CardType.Mastercard -> R.drawable.ic_mastercard
        CardType.Visa -> R.drawable.ic_visa
        CardType.UnionPay -> R.drawable.ic_union_pay
        CardType.Unknown -> R.drawable.ic_mastercard
    }
}

sealed class CardType: Serializable {
    object Mastercard : CardType()
    object Visa : CardType()
    object UnionPay : CardType()
    object Unknown : CardType()
}