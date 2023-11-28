package com.example.hotel.adapter

import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.adapterdelegate.adapter.ViewType
import com.example.adapterdelegate.adapter.ViewTypeDelegateAdapter
import com.example.hotel.R
import com.example.hotel.wrappers.WrapperPhoto
import com.example.hotel.model.Room
import com.example.hotel.utils.inflate
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RoomsDelegateAdapter(var itemClickListener: ((Room) -> Unit)?) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = RoomViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as RoomViewHolder
        holder.bind(item as Room)
    }

    inner class RoomViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.item_room)) {

        val roomName = itemView.findViewById<TextView>(R.id.room_name)
        val roomPrice = itemView.findViewById<TextView>(R.id.room_price)
        val pricePer = itemView.findViewById<TextView>(R.id.price_per)
        val btChooseRoom = itemView.findViewById<Button>(R.id.bt_choose_room)
        val moreRoomLayout = itemView.findViewById<LinearLayout>(R.id.more_room_layout)
        val vpHotel = itemView.findViewById<ViewPager2>(R.id.vp_hotel)
        val tabLayout = itemView.findViewById<TabLayout>(R.id.tab_layout)
        val layoutPeculiarities = itemView.findViewById<LinearLayout>(R.id.pecul_layout)


        fun bind(room: Room) {
            roomName.text = room.name
            val price = String.format("%,d", room.price).replace(",", " ") +
                    roomName.context.getString(R.string.rub)
            roomPrice.text = price
            pricePer.text = room.price_per

            btChooseRoom.setOnClickListener {
                Log.i("MyTag", " itemClickListener $itemClickListener")
                itemClickListener?.invoke(room)
            }

            moreRoomLayout.setOnClickListener { }

            val wrapperPhotos: List<ViewType> = room.image_urls.map { WrapperPhoto(it) }
            val adapter = ContentAdapter()
            adapter.items = wrapperPhotos
            vpHotel.adapter = adapter

            TabLayoutMediator(tabLayout, vpHotel) { _, _ ->
            }.attach()

            Log.i("MyTag", "layoutPeculiarities $layoutPeculiarities")
            setPeculiaritiesLayout(layoutPeculiarities, room.peculiarities)
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