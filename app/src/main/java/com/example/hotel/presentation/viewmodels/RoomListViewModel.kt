package com.example.hotel.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.domain.models.Room
import com.example.hotel.domain.repositories.NetworkRepository
import com.example.hotel.utils.wrappers.Response
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomListViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    private val _rooms = MutableSharedFlow<List<Room>>()
    val rooms = _rooms.asSharedFlow()

    fun getRooms(){
        viewModelScope.launch {
            val response = repository.getRooms()
            when (response) {
                is Response.Success -> {
                    _rooms.emit(response.data.rooms)
                    Log.i("MyTag", "response.data.rooms ${response.data.rooms}")
                }
                is Response.Error -> {
                    Log.i("MyTag", "Error $response")
                }
            }
        }
    }

}