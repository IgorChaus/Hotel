package com.example.hotel.utils

import android.util.TypedValue
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.hotel.R
import com.example.hotel.data.models.HotelDTO
import com.example.hotel.data.models.ReservationDTO
import com.example.hotel.data.models.RoomDTO
import com.example.hotel.data.models.RoomsDTO
import com.example.hotel.domain.models.Hotel
import com.example.hotel.domain.models.Reservation
import com.example.hotel.domain.models.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


fun setPeculiaritiesLayout(view: LinearLayout, peculiarities: List<String>) {

    view.viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            view.viewTreeObserver.removeOnGlobalLayoutListener(this)

            val maxWidth = view.width

            var currentLineWidth = 0
            var currentRowLayout = LinearLayout(view.context)
            currentRowLayout.orientation = LinearLayout.HORIZONTAL
            view.addView(currentRowLayout)

            for (peculiarity in peculiarities) {
                val peculiarityTextView = TextView(view.context)
                peculiarityTextView.text = peculiarity
                val backgroundText = ContextCompat.getDrawable(
                    view.context,
                    R.drawable.rounded_corners_peculiarity
                )
                peculiarityTextView.background = backgroundText
                val colorText = ContextCompat.getColor(view.context, R.color.grey_600)
                peculiarityTextView.setTextColor(colorText)
                peculiarityTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.toFloat())

                val densityDpi = view.context.resources.displayMetrics.densityDpi
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
                    currentRowLayout = LinearLayout(view.context)
                    currentRowLayout.orientation = LinearLayout.HORIZONTAL
                    view.addView(currentRowLayout)
                    currentLineWidth = 0
                }

                currentRowLayout.addView(peculiarityTextView)
                currentLineWidth += peculiarityTextView.measuredWidth
            }
        }
    })
}

fun HotelDTO.toModel(): Hotel {
    return Hotel(
        id = this.id,
        name = this.name,
        address = this.adress,
        minimalPrice = this.minimal_price,
        priceForIt = this.price_for_it,
        rating = this.rating,
        ratingName = this.rating_name,
        imageUrls = this.image_urls,
        aboutTheHotel = this.about_the_hotel
    )
}

fun RoomDTO.toModel(): Room {
    return Room(
        id = this.id,
        name = this.name,
        price = this.price,
        pricePer = this.price_per,
        peculiarities = this.peculiarities,
        imageUrls = this.image_urls
    )
}

fun RoomsDTO.toModel(): List<Room>{
    return  this.rooms.map {
            it.toModel()
        }
}

fun ReservationDTO.toModel(): Reservation{
    return Reservation(
        id = this.id,
        hotelName = this.hotel_name,
        hotelAddress = this.hotel_adress,
        horating = this.horating,
        ratingName = this.rating_name,
        departure = this.departure,
        arrivalCountry = this.arrival_country,
        tourDateStart = this.tour_date_start,
        tourDateStop = this.tour_date_stop,
        numberOfNights = this.number_of_nights,
        room = this.room,
        nutrition = this.nutrition,
        tourPrice = this.tour_price,
        fuelCharge = this.fuel_charge,
        serviceCharge = this.service_charge
    )
}

fun <T> Flow<T>.repeatOnCreated(lifecycleOwner: LifecycleOwner) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            this@repeatOnCreated.collect()
        }
    }
}

fun <T> Flow<T>.repeatOnCreated(lifecycleOwner: LifecycleOwner, action: suspend (T) -> Unit) {
    onEach { action(it) }
        .repeatOnCreated(lifecycleOwner)
}
