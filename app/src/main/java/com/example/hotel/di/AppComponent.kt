package com.example.hotel.di

import android.app.Application
import android.content.Context
import com.example.hotel.HotelApp
import com.example.hotel.view.HotelScreen
import com.example.hotel.view.RoomListScreen
import com.example.hotel.view.RoomScreen
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [DataModule::class]
)
@ApplicationScope
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
        ): AppComponent
    }

    fun inject(app: HotelApp)

    fun inject(fragment: HotelScreen)
    fun inject(fragment: RoomListScreen)
    fun inject(fragment: RoomScreen)

}