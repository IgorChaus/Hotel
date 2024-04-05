package com.example.hotel.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class TouristData(
    val touristList: MutableMap<Pair<Int, Int>, String>,
) : Parcelable
