package com.android.fintech_demo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.fintech_demo.domain.model.CardModel

@Dao
interface CardsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCards(cards: List<CardModel>)

    @Query("SELECT * FROM cards_table")
    suspend fun getCards(): List<CardModel>

    @Query("DELETE FROM cards_table")
    fun clear()
}