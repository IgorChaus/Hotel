package com.example.hotel.domain.repositories

import com.example.hotel.domain.models.Hotel
import com.example.hotel.domain.models.Reservation
import com.example.hotel.domain.models.Room
import com.example.hotel.domain.models.Response
import io.reactivex.rxjava3.core.Single

interface NetworkRepository {

    fun getHotel(): Single<Hotel>

    fun getRooms(): Single<List<Room>>

    fun getReservation(): Single<Reservation>
}