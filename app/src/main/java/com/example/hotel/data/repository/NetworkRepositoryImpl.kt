package com.example.hotel.data.repository

import com.example.hotel.domain.models.Hotel
import com.example.hotel.domain.models.Reservation
import com.example.hotel.domain.models.Rooms
import com.example.hotel.domain.repositories.NetworkRepository
import com.example.hotel.data.remote.RetrofitApi
import com.example.hotel.utils.wrappers.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val service: RetrofitApi
): NetworkRepository {

    override suspend fun getHotel(): Response<Hotel> {
        return withContext(Dispatchers.IO) {
            handleApiCall {
                service.getHotel()
            }
        }
    }

    override suspend fun getRooms(): Response<Rooms> {
        return withContext(Dispatchers.IO) {
            handleApiCall {
                service.getRooms()
            }
        }
    }

    override suspend fun getReservation(): Response<Reservation> {
        return withContext(Dispatchers.IO) {
            handleApiCall {
                service.getReservation()
            }
        }
    }

    private suspend fun <T> handleApiCall(call: suspend () -> retrofit2.Response<T>): Response<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                Response.Success(data = response.body()!!)
            } else {
                Response.Error(response.code().toString())
            }
        } catch (e: HttpException) {
            Response.Error(e.message ?: "HttpException")
        } catch (e: IOException) {
            Response.Error("IOException")
        } catch (e: Exception) {
            Response.Error(e.message ?: "Exception")
        }
    }
}
