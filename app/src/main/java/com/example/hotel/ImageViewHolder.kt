package com.example.hotel

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.iv_photo)
}