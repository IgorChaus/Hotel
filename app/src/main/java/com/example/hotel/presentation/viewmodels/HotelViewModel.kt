package com.example.hotel.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.domain.models.Hotel
import com.example.hotel.domain.repositories.NetworkRepository
import com.example.hotel.domain.models.Response
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HotelViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    private val _hotel = BehaviorSubject.create<Hotel>()
    val hotel: Observable<Hotel>
        get() = _hotel.hide()

    private val disposables = CompositeDisposable()

    fun getHotel() {
        disposables.add(
            repository.getHotel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ hotel ->
                    _hotel.onNext(hotel)
                }, { error ->
                    handleError(error)
                })
        )
    }

    private fun handleError(error: Throwable) {
        Log.i("MyTag", "Error: $error")
    }

    fun clearDisposables() {
        disposables.clear()
    }

}