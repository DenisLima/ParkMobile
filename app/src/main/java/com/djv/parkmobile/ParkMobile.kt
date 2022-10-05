package com.djv.parkmobile

import android.app.Application
import com.djv.data.di.dataModule
import com.djv.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ParkMobile: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ParkMobile)
            modules(listOf(dataModule, presentationModule))
        }
    }
}