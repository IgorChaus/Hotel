package com.example.hotel.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotel.domain.models.Room
import com.example.hotel.domain.repositories.NetworkRepository
import com.example.hotel.domain.models.Response
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomListViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    private val _rooms = BehaviorSubject.create<List<Room>>()
    val rooms: Observable<List<Room>>
        get() = _rooms.hide()

    private val disposables = CompositeDisposable()


    fun getRooms(){
        disposables.add(
            repository.getRooms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_rooms::onNext, this::handleError)

        )
    }

    private fun handleError(error: Throwable) {
        Log.i("MyTag", "Error: $error")
    }

    fun clearDisposables() {
        disposables.clear()
    }

}