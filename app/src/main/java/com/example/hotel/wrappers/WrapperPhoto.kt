package com.example.hotel.wrappers

import com.example.adapterdelegate.adapter.ViewType
import com.example.hotel.adapter.ContentAdapter

data class WrapperPhoto(
    val image: String
): ViewType {
    override fun getViewType() = ContentAdapter.PHOTO
}
