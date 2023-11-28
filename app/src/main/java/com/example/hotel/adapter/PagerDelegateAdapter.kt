package com.example.hotel.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adapterdelegate.adapter.ViewType
import com.example.adapterdelegate.adapter.ViewTypeDelegateAdapter
import com.example.hotel.R
import com.example.hotel.wrappers.WrapperPhoto
import com.example.hotel.utils.inflate

class PagerDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = HotelPhotoViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as HotelPhotoViewHolder
        holder.bind(item as WrapperPhoto)
    }

    inner class HotelPhotoViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.photo_hotel)) {

        private val ivPhoto = itemView.findViewById<ImageView>(R.id.iv_photo)

        fun bind(item: WrapperPhoto){
            Glide.with(itemView.context).load(item.image).into(ivPhoto)
        }

    }
}