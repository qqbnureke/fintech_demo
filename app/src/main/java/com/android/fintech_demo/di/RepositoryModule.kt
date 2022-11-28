package com.android.fintech_demo.di

import com.android.fintech_demo.domain.Repository
import com.android.fintech_demo.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindRepository(baseRepositoryImpl: RepositoryImpl): Repository
}