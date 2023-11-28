package com.example.hotel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adapterdelegate.adapter.ViewType
import com.example.adapterdelegate.adapter.ViewTypeDelegateAdapter
import com.example.hotel.databinding.PhotoHotelBinding
import com.example.hotel.wrappers.WrapperPhoto

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