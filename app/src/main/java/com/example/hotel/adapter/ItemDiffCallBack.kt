package com.example.hotel.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.hotel.model.Room

object ItemDiffCallBack : DiffUtil.ItemCallback<Room>() {
    override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem == newItem
    }
}
