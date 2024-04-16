package com.example.hotel.presentation.adapter


import com.example.hotel.presentation.adapter.ContentAdapter
import com.example.hotel.presentation.adapter.ViewType

data class WrapperPhoto(
    val image: String
): ViewType {
    override fun getViewType() = ContentAdapter.PHOTO
}
