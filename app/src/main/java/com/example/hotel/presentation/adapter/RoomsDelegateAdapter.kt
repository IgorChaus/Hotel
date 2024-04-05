package com.example.hotel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotel.R
import com.example.hotel.databinding.ItemRoomBinding
import com.example.hotel.domain.models.Room
import com.example.hotel.utils.setPeculiaritiesLayout
import com.example.hotel.utils.wrappers.WrapperPhoto
import com.google.android.material.tabs.TabLayoutMediator

class RoomsDelegateAdapter(var itemClickListener: ((Room) -> Unit)?) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = RoomViewHolder(
       ItemRoomBinding.inflate(
            LayoutInflater
                .from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as RoomViewHolder
        holder.bind(item as Room)
    }

    inner class RoomViewHolder(private val binding: ItemRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(room: Room) {
            binding.roomName.text = room.name
            val price = String.format("%,d", room.price).replace(",", " ") +
                    binding.roomName.context.getString(R.string.rub)
            binding.roomPrice.text = price
            binding.pricePer.text = room.price_per

            binding.btChooseRoom.setOnClickListener {
                itemClickListener?.invoke(room)
            }

            binding.moreRoomLayout.setOnClickListener { }

            val wrapperPhotos: List<ViewType> = room.image_urls.map { WrapperPhoto(it) }
            val adapter = ContentAdapter()
            adapter.items = wrapperPhotos
            binding.vpHotel.adapter = adapter

            TabLayoutMediator(binding.tabLayout, binding.vpHotel) { _, _ ->
            }.attach()

            setPeculiaritiesLayout(binding.peculLayout, room.peculiarities)
        }
    }
}