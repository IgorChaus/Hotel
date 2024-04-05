package com.example.hotel.di

import androidx.lifecycle.ViewModel
import com.example.hotel.presentation.viewmodels.HotelViewModel
import com.example.hotel.presentation.viewmodels.RoomListViewModel
import com.example.hotel.presentation.viewmodels.RoomViewModel
import com.ripotu.controlreality.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(HotelViewModel::class)
    fun bindHotelViewModel(viewModel: HotelViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomListViewModel::class)
    fun bindRoomListViewModel(viewModel: RoomListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomViewModel::class)
    fun bindRoomViewModel(viewModel: RoomViewModel): ViewModel

}