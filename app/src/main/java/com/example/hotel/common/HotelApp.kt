package com.example.hotel.common

import android.app.Application
import android.content.Context
import com.example.hotel.di.AppComponent
import com.example.hotel.di.DaggerAppComponent

class HotelApp : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)

        super.onCreate()
    }


}

// allows to take appComponent from any place with only context
val Context.appComponent: AppComponent
    get() = when (this) {
        is HotelApp -> appComponent
        else -> (this.applicationContext as HotelApp).appComponent
    }

