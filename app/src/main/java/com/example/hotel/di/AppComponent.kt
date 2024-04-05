package com.example.hotel.di

import android.app.Application
import com.example.hotel.HotelApp
import com.example.hotel.presentation.views.HotelScreen
import com.example.hotel.presentation.views.RoomListScreen
import com.example.hotel.presentation.views.RoomScreen
import com.ripotu.controlreality.di.ViewModelBuilderModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelsModule::class,
        ViewModelBuilderModule::class
    ]
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