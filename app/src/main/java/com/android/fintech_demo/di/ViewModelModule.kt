package com.android.fintech_demo.di

import androidx.lifecycle.ViewModel
import com.android.fintech_demo.presentation.fragments.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @ClassKey(MainViewModel::class)
    @IntoMap
    abstract fun bindMainViewModel(repoViewModel: MainViewModel): ViewModel
}