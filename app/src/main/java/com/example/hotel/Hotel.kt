package com.example.hotel

data class Hotel(
    val id: Int,
    val name: String,
    val adress: String,
    val minimal_price: Int,
    val price_for_it: String,
    val rating: Int,
    val rating_name: String,
    val image_urls: List<String>,
    val about_the_hotel: AboutTheHotel
){
    data class AboutTheHotel(
        val description: String,
        val peculiarities: List<String>
    )
}