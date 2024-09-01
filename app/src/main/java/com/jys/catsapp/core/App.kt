package com.jys.catsapp.core

import android.app.Application
import com.jys.catsapp.di.databaseModule
import com.jys.catsapp.di.homeModule
import com.jys.catsapp.di.networkModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App :Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(networkModule, homeModule, databaseModule)
        }
    }
}