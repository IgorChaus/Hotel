package com.example.hotel.listadapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hotel.R
import com.example.hotel.model.Room
import com.example.hotel.databinding.ItemRoomBinding
import com.example.hotel.pageradapter.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class RoomListAdapter: ListAdapter<Room, RoomViewHolder>(ItemDiffCallBack) {

    var itemClickListener: ((Room) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RoomViewHolder(
        ItemRoomBinding.inflate(
            LayoutInflater
                .from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = getItem(position)
        with(holder.binding) {
            roomName.text = room.name
            val price = String.format("%,d", room.price).replace(",", " ") +
                    roomName.context.getString(R.string.rub)
            roomPrice.text = price
            pricePer.text = room.price_per

            btChooseRoom.setOnClickListener {
                if (holder.adapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                itemClickListener?.invoke(room)
            }

            moreRoomLayout.setOnClickListener {  }

        }

        val adapter = PagerAdapter(room.image_urls)
        holder.binding.vpHotel.adapter = adapter
        TabLayoutMediator(holder.binding.tabLayout, holder.binding.vpHotel) { _, _ ->
        }.attach()

        val layoutPeculiarities = holder.binding.peculiaritiesLayout
        setPeculiaritiesLayout(layoutPeculiarities, room.peculiarities)
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