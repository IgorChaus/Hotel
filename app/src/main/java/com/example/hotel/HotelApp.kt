package com.example.hotel

import android.app.Application
import com.example.hotel.di.DaggerApplicationComponent

class HotelApp : Application() {
    val component = DaggerApplicationComponent.factory().create(this)
}