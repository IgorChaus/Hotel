package com.example.hotel.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.R
import com.example.hotel.model.Reservation
import com.example.hotel.source.NetworkRepository
import com.example.hotel.wrappers.Response
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomViewModel @Inject constructor(
    private val repository: NetworkRepository,
    private val application: Application
) : ViewModel() {

    private val _reservation: MutableLiveData<Reservation> = MutableLiveData()
    val reservation: LiveData<Reservation>
        get() = _reservation

    private val _emailError: MutableLiveData<Boolean> = MutableLiveData()
    val emailError: LiveData<Boolean>
        get() = _emailError

    private val _showEmptyFields: MutableLiveData<Boolean> = MutableLiveData(false)
    val showEmptyFields: LiveData<Boolean>
        get() = _showEmptyFields

    var listTouristGroups = arrayListOf("Первый турист")
    private val touristInfo = listOf(
        "Имя",
        "Фамилия",
        "Дата рождения",
        "Гражданство",
        "Номер загранпаспорта",
        "Срок действия загранпаспорта"
    )
    var listTouristsChild = hashMapOf(
        "Первый турист" to touristInfo,
    )

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

    fun addNewTourist(){
        val numberTouristString = numberToOrdinal(listTouristGroups.size + 1) + " " +
                application.getString(R.string.tourist)
        listTouristGroups.add(numberTouristString)
        listTouristsChild[numberTouristString] = touristInfo
    }

    fun isAnyFieldEmpty(touristList: MutableMap<Pair<Int, Int>, String>): Boolean {
        for (i in 0 until  listTouristGroups.size){
            for (j in 0 .. 5){
                if(touristList[Pair(i, j)] == null) {
                    _showEmptyFields.value = true
                    return true
                }
            }
        }
        _showEmptyFields.value = false
        return false
    }

    private fun numberToOrdinal(n: Int): String {
        val numbers = listOf(
            application.getString(R.string.first),
            application.getString(R.string.second),
            application.getString(R.string.third),
            application.getString(R.string.fourth),
            application.getString(R.string.fifth),
            application.getString(R.string.sixth),
            application.getString(R.string.seventh),
            application.getString(R.string.eighth),
            application.getString(R.string.ninth),
            application.getString(R.string.tenth)
        )
        return numbers[n-1]
    }

}