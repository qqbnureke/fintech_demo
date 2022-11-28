package com.android.fintech_demo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.fintech_demo.domain.model.CardModel
import com.android.fintech_demo.domain.model.CurrencyModel
import com.android.fintech_demo.data.utils.ConverterUtils

@Database(entities = [CurrencyModel::class, CardModel::class], version = 1)
@TypeConverters(ConverterUtils::class)
abstract class Database : RoomDatabase() {

    abstract fun getCurrencyDAO(): CurrencyDao

    abstract fun getCardsDAO(): CardsDao

}