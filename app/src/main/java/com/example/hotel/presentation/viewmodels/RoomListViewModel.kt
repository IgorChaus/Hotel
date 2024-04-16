package com.example.hotel.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.domain.models.Room
import com.example.hotel.domain.repositories.NetworkRepository
import com.example.hotel.utils.wrappers.Response
import kotlinx.coroutines.CoroutineExceptionHandler
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
        viewModelScope.launch(coroutineExceptionHandler) {
            val response = repository.getRooms()
            when (response) {
                is Response.Success -> _rooms.emit(response.data)
                is Response.Error -> handleException(response.exception)
            }
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleException(exception)
    }

    private fun handleException(throwable: Throwable?) {
        Log.i("MyTag", "Exception $throwable")
    }

}