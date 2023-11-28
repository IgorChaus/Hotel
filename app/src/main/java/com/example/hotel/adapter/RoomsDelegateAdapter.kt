package com.example.hotel.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.adapterdelegate.adapter.ViewType
import com.example.adapterdelegate.adapter.ViewTypeDelegateAdapter
import com.example.hotel.R
import com.example.hotel.databinding.ItemRoomBinding
import com.example.hotel.model.Room
import com.example.hotel.wrappers.WrapperPhoto
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

    private fun setPeculiaritiesLayout(
        layoutPeculiarities: LinearLayout,
        peculiarities: List<String>
    ) {
        layoutPeculiarities.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                layoutPeculiarities.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val maxWidth = layoutPeculiarities.width

                var currentLineWidth = 0
                var currentRowLayout = LinearLayout(layoutPeculiarities.context)
                currentRowLayout.orientation = LinearLayout.HORIZONTAL
                layoutPeculiarities.addView(currentRowLayout)

                for (peculiarity in peculiarities) {
                    val peculiarityTextView = TextView(layoutPeculiarities.context)
                    peculiarityTextView.text = peculiarity
                    val backgroundText = ContextCompat.getDrawable(
                        layoutPeculiarities.context,
                        R.drawable.rounded_corners_peculiarity
                    )
                    peculiarityTextView.background = backgroundText
                    val colorText = ContextCompat.getColor(
                        layoutPeculiarities.context,
                        R.color.grey_600
                    )
                    peculiarityTextView.setTextColor(colorText)
                    peculiarityTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.toFloat())

                    val densityDpi = layoutPeculiarities.resources.displayMetrics.densityDpi
                    val leftPaddingDp = densityDpi / 160 * 10
                    val rightPaddingDp = densityDpi / 160 * 10
                    val topPaddingDp = densityDpi / 160 * 5
                    val bottomPaddingDp = densityDpi / 160 * 5
                    peculiarityTextView.setPadding(
                        leftPaddingDp,
                        topPaddingDp,
                        rightPaddingDp,
                        bottomPaddingDp
                    )

                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    layoutParams.setMargins(8, 8, 8, 8)

                    peculiarityTextView.layoutParams = layoutParams
                    peculiarityTextView.measure(0, 0)

                    if (currentLineWidth + peculiarityTextView.measuredWidth > maxWidth) {
                        currentRowLayout = LinearLayout(layoutPeculiarities.context)
                        currentRowLayout.orientation = LinearLayout.HORIZONTAL
                        layoutPeculiarities.addView(currentRowLayout)
                        currentLineWidth = 0
                    }

                    currentRowLayout.addView(peculiarityTextView)
                    currentLineWidth += peculiarityTextView.measuredWidth
                }
            }
        })
    }

}