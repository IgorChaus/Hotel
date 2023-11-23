package com.example.hotel.network

import com.example.hotel.model.Hotel
import com.example.hotel.model.Reservation
import com.example.hotel.model.Rooms
import retrofit2.Response
import retrofit2.http.GET

// https://run.mocky.io/v3/d144777c-a67f-4e35-867a-cacc3b827473

// https://run.mocky.io/v3/8b532701-709e-4194-a41c-1a903af00195

// https://run.mocky.io/v3/63866c74-d593-432c-af8e-f279d1a8d2ff

interface RetrofitApi {

    @GET("d144777c-a67f-4e35-867a-cacc3b827473")
    suspend fun getHotel(
    ) : Response<Hotel>

    @GET("8b532701-709e-4194-a41c-1a903af00195")
    suspend fun getRooms(
    ) : Response<Rooms>

    @GET("63866c74-d593-432c-af8e-f279d1a8d2ff")
    suspend fun getReservation(
    ) : Response<Reservation>
}