package com.example.hotel.presentation.viewmodels

import android.app.Application
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.R
import com.example.hotel.domain.models.Reservation
import com.example.hotel.domain.repositories.NetworkRepository
import com.example.hotel.domain.models.Response
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomViewModel @Inject constructor(
    private val repository: NetworkRepository,
    private val application: Application
) : ViewModel() {

    private val _reservation = MutableSharedFlow<Reservation>()
    val reservation = _reservation.asSharedFlow()

    private val _emailError = MutableSharedFlow<Boolean>()
    val emailError = _emailError.asSharedFlow()

    private val _showEmptyFields = MutableSharedFlow<Boolean>()
    val showEmptyFields = _showEmptyFields.asSharedFlow()

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

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleException(exception)
    }

    init {
        getReservation()
    }

    private fun getReservation() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val response = repository.getReservation()
            when (response) {
                is Response.Success -> _reservation.emit(response.data)
                is Response.Error -> handleException(response.exception)
            }
        }
    }

    private fun handleException(throwable: Throwable?) {
        Log.i("MyTag", "Error $throwable")
    }

    fun checkEmail(email: String){
        viewModelScope.launch {
            _emailError.emit(!Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty())
        }
    }

    fun resetError(){
        viewModelScope.launch{
            _emailError.emit(false)
        }

    }

    fun addNewTourist(){
        val numberTouristString = numberToOrdinal(listTouristGroups.size + 1) + " " +
                application.getString(R.string.tourist)
        listTouristGroups.add(numberTouristString)
        listTouristsChild[numberTouristString] = touristInfo
    }

    fun isAnyFieldEmpty(touristList: MutableMap<Pair<Int, Int>, String>): Boolean {

        for (i in 0 until listTouristGroups.size) {
            for (j in 0..5) {
                if (touristList[Pair(i, j)] == null) {
                    viewModelScope.launch { _showEmptyFields.emit(true) }
                    return true
                }
            }
        }
        viewModelScope.launch { _showEmptyFields.emit(false) }
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