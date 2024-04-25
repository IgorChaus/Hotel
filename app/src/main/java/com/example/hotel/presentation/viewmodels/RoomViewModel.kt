package com.example.hotel.presentation.viewmodels

import android.app.Application
import android.util.Log
import android.util.Patterns
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.R
import com.example.hotel.domain.models.Reservation
import com.example.hotel.domain.repositories.NetworkRepository
import com.example.hotel.domain.models.Response
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomViewModel @Inject constructor(
    private val repository: NetworkRepository,
    private val application: Application
) : ViewModel() {

    private val _reservation = BehaviorSubject.create<Reservation>()
    val reservation: Observable<Reservation>
        get() = _reservation.hide()

    private val _emailError = BehaviorSubject.create<Boolean>()
    val emailError: Observable<Boolean>
        get() = _emailError.hide()

    private val _showEmptyFields = BehaviorSubject.create<Boolean>()
    val showEmptyFields: Observable<Boolean>
        get() = _showEmptyFields.hide()

    private val disposables = CompositeDisposable()

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

    init {
        getReservation()
    }

    private fun getReservation(){
        disposables.add(
            repository.getReservation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_reservation::onNext, this::handleError)
        )
    }

    private fun handleError(error: Throwable) {
        Log.i("MyTag", "Error: $error")
    }

    fun checkEmail(email: String){
        _emailError.onNext(!Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty())
    }

    fun resetError() {
        _emailError.onNext(false)
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
                    _showEmptyFields.onNext(true)
                    return true
                }
            }
        }
        _showEmptyFields.onNext(false)
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

    fun clearDisposables() {
        disposables.clear()
    }

}