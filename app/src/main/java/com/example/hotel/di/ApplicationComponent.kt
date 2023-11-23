package com.example.hotel.di

import com.example.hotel.view.HotelScreen
import com.example.hotel.view.RoomListScreen
import com.example.hotel.view.RoomScreen
import dagger.Component

@Component(modules = [DataModule::class])
interface ApplicationComponent {

    fun inject(fragment: HotelScreen)
    fun inject(fragment: RoomListScreen)
    fun inject(fragment: RoomScreen)

}