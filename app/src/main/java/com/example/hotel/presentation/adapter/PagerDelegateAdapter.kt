package com.example.hotel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotel.databinding.PhotoHotelBinding

class PagerDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = HotelPhotoViewHolder(
        PhotoHotelBinding.inflate(
            LayoutInflater
                .from(parent.context), parent, false
        )
    )
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as HotelPhotoViewHolder
        holder.bind(item as WrapperPhoto)
    }

    inner class HotelPhotoViewHolder(private val binding: PhotoHotelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WrapperPhoto){
            Glide.with(itemView.context).load(item.image).into(binding.ivPhoto)
        }
    }
}