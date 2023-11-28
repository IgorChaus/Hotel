package com.example.hotel.model

import com.example.adapterdelegate.adapter.ViewType
import com.example.hotel.adapter.AdapterConstants

data class Room(
    val id: Int,
    val name: String,
    val price: Int,
    val price_per: String,
    val peculiarities: List<String>,
    val image_urls: List<String>
): ViewType {
    override fun getViewType() = AdapterConstants.Room
}