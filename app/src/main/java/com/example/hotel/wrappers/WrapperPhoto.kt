package com.example.hotel.wrappers


import com.example.hotel.adapter.ContentAdapter
import com.example.hotel.adapter.ViewType

data class WrapperPhoto(
    val image: String
): ViewType {
    override fun getViewType() = ContentAdapter.PHOTO
}
