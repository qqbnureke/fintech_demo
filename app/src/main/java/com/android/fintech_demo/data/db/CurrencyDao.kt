package com.android.fintech_demo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.fintech_demo.domain.model.CurrencyModel

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrency(currency : CurrencyModel)

    @Query("SELECT * FROM currency_table")
    suspend fun getCurrency() : CurrencyModel

    @Query("DELETE FROM currency_table")
    fun clear()
}