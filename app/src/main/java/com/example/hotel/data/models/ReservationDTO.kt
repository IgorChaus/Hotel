package com.example.hotel.data.models

data class ReservationDTO(
    val id: Int,
    val hotel_name: String,
    val hotel_adress: String,
    val horating: Int,
    val rating_name: String,
    val departure: String,
    val arrival_country: String,
    val tour_date_start: String,
    val tour_date_stop: String,
    val number_of_nights: Int,
    val room: String,
    val nutrition: String,
    val tour_price: Int,
    val fuel_charge: Int,
    val service_charge: Int
)