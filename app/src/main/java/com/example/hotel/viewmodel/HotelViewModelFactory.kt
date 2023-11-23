package com.example.hotel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hotel.source.NetworkRepository
import javax.inject.Inject

class HotelViewModelFactory @Inject constructor(private val repository: NetworkRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HotelViewModel::class.java))
            return HotelViewModel(repository) as T
        throw RuntimeException("Unknown ViewModel class $modelClass")
    }
}