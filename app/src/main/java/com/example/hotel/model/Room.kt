package com.example.hotel.model

import com.example.hotel.adapter.ContentAdapter
import com.example.hotel.adapter.ViewType

data class Room(
    val id: Int,
    val name: String,
    val price: Int,
    val price_per: String,
    val peculiarities: List<String>,
    val image_urls: List<String>
): ViewType {
    override fun getViewType() = ContentAdapter.ROOM
}