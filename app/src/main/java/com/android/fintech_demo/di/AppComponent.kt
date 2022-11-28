package com.android.fintech_demo.di

import android.content.Context
import com.android.fintech_demo.presentation.MainActivity
import com.android.fintech_demo.presentation.fragments.main.MainFragment
import com.android.fintech_demo.presentation.fragments.my_cards.MyCardsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, ViewModelModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun inject(mainFragment: MainFragment)

    fun inject(myCardsFragment: MyCardsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}