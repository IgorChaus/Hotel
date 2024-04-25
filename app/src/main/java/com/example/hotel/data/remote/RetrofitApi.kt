package com.example.hotel.data.remote

import com.example.hotel.data.models.HotelDTO
import com.example.hotel.data.models.ReservationDTO
import com.example.hotel.data.models.RoomsDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RetrofitApi {

    @GET("d144777c-a67f-4e35-867a-cacc3b827473")
    fun getHotel() : Single<HotelDTO>

    @GET("8b532701-709e-4194-a41c-1a903af00195")
    fun getRooms(
    ) : Single<RoomsDTO>

    @GET("63866c74-d593-432c-af8e-f279d1a8d2ff")
    fun getReservation(
    ) : Single<ReservationDTO>
}