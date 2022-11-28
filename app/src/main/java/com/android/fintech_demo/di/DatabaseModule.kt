package com.android.fintech_demo.di

import android.content.Context
import androidx.room.Room
import com.android.fintech_demo.data.db.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, "Cards_db").build()
    }
}