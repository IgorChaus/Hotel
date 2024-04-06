package com.example.hotel.domain.models

import com.example.hotel.presentation.adapter.ContentAdapter
import com.example.hotel.presentation.adapter.ViewType

data class Room(
    val id: Int,
    val name: String,
    val price: Int,
    val pricePer: String,
    val peculiarities: List<String>,
    val imageUrls: List<String>
): ViewType {
    override fun getViewType() = ContentAdapter.ROOM
}