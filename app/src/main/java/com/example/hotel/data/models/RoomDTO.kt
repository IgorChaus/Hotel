package com.example.hotel.data.models

import com.example.hotel.presentation.adapter.ContentAdapter
import com.example.hotel.presentation.adapter.ViewType

data class RoomDTO(
    val id: Int,
    val name: String,
    val price: Int,
    val price_per: String,
    val peculiarities: List<String>,
    val image_urls: List<String>
): ViewType {
    override fun getViewType() = ContentAdapter.ROOM
}