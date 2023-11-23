package com.example.hotel.source

import com.example.hotel.model.Hotel
import com.example.hotel.model.Reservation
import com.example.hotel.model.Rooms
import com.example.hotel.network.RetrofitApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import com.example.hotel.network.RetrofitInstance
import com.example.hotel.wrappers.Response
import java.io.IOException
import javax.inject.Inject

class NetworkRepository @Inject constructor(val service: RetrofitApi) {

    suspend fun getHotel(): Response<Hotel> {
        return withContext(Dispatchers.IO) {
            handleApiCall {
                service.getHotel()
            }
        }
    }

    suspend fun getRooms(): Response<Rooms> {
        return withContext(Dispatchers.IO) {
            handleApiCall {
                service.getRooms()
            }
        }
    }

    suspend fun getReservation(): Response<Reservation> {
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
