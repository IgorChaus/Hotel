package com.example.hotel.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.data.models.HotelDTO
import com.example.hotel.domain.models.Hotel
import com.example.hotel.domain.repositories.NetworkRepository
import com.example.hotel.utils.wrappers.Response
import kotlinx.coroutines.launch
import javax.inject.Inject

class HotelViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {


    private val _hotel: MutableLiveData<Hotel> = MutableLiveData()
    val hotel: LiveData<Hotel>
        get() = _hotel

    init{
        getHotel()
    }

    private fun getHotel(){
        viewModelScope.launch {
            val response = repository.getHotel()
            when (response) {
                is Response.Success -> {
                    _hotel.value = response.data
                }
                is Response.Error -> {
                    Log.i("MyTag", "Error $response")
                }
            }
        }
    }

}