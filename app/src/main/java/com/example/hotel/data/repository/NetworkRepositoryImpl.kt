package com.example.hotel.data.repository

import com.example.hotel.common.toModel
import com.example.hotel.data.remote.RetrofitApi
import com.example.hotel.domain.models.Hotel
import com.example.hotel.domain.models.Reservation
import com.example.hotel.domain.models.Room
import com.example.hotel.domain.repositories.NetworkRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val service: RetrofitApi
): NetworkRepository {

    override fun getHotel(): Single<Hotel> {
        return service.getHotel()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { hotelDTO ->
                if (hotelDTO != null) {
                    hotelDTO.toModel()
                } else {
                    throw Exception("No data available")
                }
            }
            .onErrorResumeNext { throwable: Throwable ->
                Single.error(Exception("Failed to retrieve data", throwable))
            }
    }

    override fun getRooms(): Single<List<Room>> {
        return service.getRooms()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { roomDTO ->
                if (roomDTO != null) {
                    roomDTO.toModel()
                } else {
                    throw Exception("No data available")
                }
            }
            .onErrorResumeNext { throwable: Throwable ->
                Single.error(Exception("Failed to retrieve data", throwable))
            }

    }

    override fun getReservation(): Single<Reservation> {
        return service.getReservation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { reservationDTO ->
                if (reservationDTO != null) {
                    reservationDTO.toModel()
                } else {
                    throw Exception("No data available")
                }
            }
            .onErrorResumeNext { throwable: Throwable ->
                Single.error(Exception("Failed to retrieve data", throwable))
            }
    }

}
