package com.example.hotel.data.repository

import com.example.hotel.common.toModel
import com.example.hotel.data.models.HotelDTO
import com.example.hotel.data.models.ReservationDTO
import com.example.hotel.data.models.RoomsDTO
import com.example.hotel.data.remote.RetrofitApi
import com.example.hotel.domain.models.Hotel
import com.example.hotel.domain.models.Reservation
import com.example.hotel.domain.models.Room
import com.example.hotel.domain.repositories.NetworkRepository
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val service: RetrofitApi
): NetworkRepository {

    override fun getHotel(): Single<Hotel> {
        return Single.create { emitter ->
            service.getHotel().enqueue(object : Callback<HotelDTO> {
                override fun onResponse(call: Call<HotelDTO>, response: Response<HotelDTO>) {
                    if (response.isSuccessful) {
                        val hotelDTO = response.body()
                        if (hotelDTO != null) {
                            val hotel = hotelDTO.toModel()
                            emitter.onSuccess(hotel)
                        } else {
                            emitter.onError(Exception("No data available"))
                        }
                    } else {
                        emitter.onError(Exception("Failed to retrieve data"))
                    }
                }

                override fun onFailure(call: Call<HotelDTO>, t: Throwable) {
                    emitter.onError(t)
                }

            })
        }
    }

    override fun getRooms(): Single<List<Room>> {
        return Single.create { emitter ->
            service.getRooms().enqueue(object : Callback<RoomsDTO> {
                override fun onResponse(call: Call<RoomsDTO>, response: Response<RoomsDTO>) {
                    if (response.isSuccessful) {
                        val roomsDTO = response.body()
                        if (roomsDTO != null) {
                            val hotel = roomsDTO.toModel()
                            emitter.onSuccess(hotel)
                        } else {
                            emitter.onError(Exception("No data available"))
                        }
                    } else {
                        emitter.onError(Exception("Failed to retrieve data"))
                    }
                }

                override fun onFailure(call: Call<RoomsDTO>, t: Throwable) {
                    emitter.onError(t)
                }

            })
        }
    }


    override fun getReservation(): Single<Reservation> {
        return Single.create<Reservation>() { emitter ->
            service.getReservation().enqueue(object : Callback<ReservationDTO> {
                override fun onResponse(
                    call: Call<ReservationDTO>,
                    response: Response<ReservationDTO>
                ) {
                    if (response.isSuccessful) {
                        val reservationDTO = response.body()
                        if (reservationDTO != null) {
                            val hotel = reservationDTO.toModel()
                            emitter.onSuccess(hotel)
                        } else {
                            emitter.onError(Exception("No data available"))
                        }
                    } else {
                        emitter.onError(Exception("Failed to retrieve data"))
                    }
                }

                override fun onFailure(call: Call<ReservationDTO>, t: Throwable) {
                    emitter.onError(t)
                }

            })
        }
    }

}
