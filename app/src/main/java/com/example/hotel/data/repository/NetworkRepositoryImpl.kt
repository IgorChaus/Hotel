package com.example.hotel.data.repository

import com.example.hotel.data.remote.RetrofitApi
import com.example.hotel.domain.models.Hotel
import com.example.hotel.domain.models.Reservation
import com.example.hotel.domain.models.Rooms
import com.example.hotel.domain.repositories.NetworkRepository
import com.example.hotel.utils.toModel
import com.example.hotel.utils.wrappers.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val service: RetrofitApi
): NetworkRepository {

    override suspend fun getHotel(): Response<Hotel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getHotel().toModel()
                Response.Success(response)
            } catch (e: Exception) {
                Response.Error(e)
            }
        }
    }

    override suspend fun getRooms(): Response<Rooms> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getRooms().toModel()
                Response.Success(response)
            } catch (e: Exception) {
                Response.Error(e)
            }
        }
    }

    override suspend fun getReservation(): Response<Reservation> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getReservation().toModel()
                Response.Success(response)
            } catch (e: Exception) {
                Response.Error(e)
            }
        }
    }

}
