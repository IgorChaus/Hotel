package com.example.hotel.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.domain.models.Room
import com.example.hotel.domain.repositories.NetworkRepository
import com.example.hotel.utils.wrappers.Response
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomListViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    private val _rooms: MutableLiveData<List<Room>> = MutableLiveData()
    val rooms: LiveData<List<Room>>
        get() = _rooms

    init{
        getRooms()
    }

    private fun getRooms(){
        viewModelScope.launch {
            val response = repository.getRooms()
            when (response) {
                is Response.Success -> {
                    _rooms.value = response.data.rooms
                }
                is Response.Error -> {
                    Log.i("MyTag", "Error ${response.errorMessage}")
                }
            }
        }
    }

}