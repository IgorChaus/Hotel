package com.example.hotel.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.model.Reservation
import com.example.hotel.source.NetworkRepository
import com.example.hotel.wrappers.Response
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    private val _reservation: MutableLiveData<Reservation> = MutableLiveData()
    val reservation: LiveData<Reservation>
        get() = _reservation

    private val _emailError: MutableLiveData<Boolean> = MutableLiveData()
    val emailError: LiveData<Boolean>
        get() = _emailError

    init{
        getReservation()
    }

    private fun getReservation(){
        viewModelScope.launch {
            val response = repository.getReservation()
            when (response) {
                is Response.Success -> {
                    _reservation.value = response.data
                }
                is Response.Error -> {
                    Log.i("MyTag", "Error ${response.errorMessage}")
                }
            }
        }
    }

    fun checkEmail(email: String){
        _emailError.value = !Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()
    }

    fun resetError(){
        _emailError.value = false
    }

}