package com.android.fintech_demo.presentation

import android.app.Application
import com.android.fintech_demo.di.AppComponent
import com.android.fintech_demo.di.DaggerAppComponent

class AppApplication : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.factory().create(this)
    }
}