package com.example.hotel.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hotel.di.ApplicationScope
import com.example.hotel.source.NetworkRepository
import javax.inject.Inject

@ApplicationScope
class ViewModelFactory@Inject constructor(
    private val repository: NetworkRepository,
    private val application: Application
) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HotelViewModel::class.java))
            return HotelViewModel(repository) as T
        if (modelClass.isAssignableFrom(RoomListViewModel::class.java))
            return RoomListViewModel(repository) as T
        if (modelClass.isAssignableFrom(RoomViewModel::class.java))
            return RoomViewModel(repository, application) as T

        throw RuntimeException("Unknown ViewModel class $modelClass")
    }
}