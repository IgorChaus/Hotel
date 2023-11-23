package com.example.hotel.pageradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotel.databinding.PhotoHotelBinding

class PagerAdapter(private val images: List<String>) : RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageViewHolder(
        PhotoHotelBinding.inflate(
            LayoutInflater
                .from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = images[position]
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.binding.ivPhoto)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}