package com.example.hotel.domain.repositories

import com.example.hotel.domain.models.Hotel
import com.example.hotel.domain.models.Reservation
import com.example.hotel.domain.models.Room
import com.example.hotel.utils.wrappers.Response

interface NetworkRepository {

    suspend fun getHotel(): Response<Hotel>

    suspend fun getRooms(): Response<List<Room>>

    suspend fun getReservation(): Response<Reservation>
}