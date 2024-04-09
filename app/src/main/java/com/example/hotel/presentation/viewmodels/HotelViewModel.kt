package com.example.hotel.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.domain.models.Hotel
import com.example.hotel.domain.repositories.NetworkRepository
import com.example.hotel.utils.wrappers.Response
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HotelViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    private val _hotel = MutableSharedFlow<Hotel>()
    val hotel = _hotel.asSharedFlow()

    fun getHotel(){
        viewModelScope.launch(coroutineExceptionHandler) {
            val response = repository.getHotel()
            when (response) {
                is Response.Success -> _hotel.emit(response.data)
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