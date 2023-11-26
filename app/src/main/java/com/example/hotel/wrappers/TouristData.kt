package com.example.hotel.wrappers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TouristData(val touristList: MutableMap<Pair<Int, Int>, String>) : Parcelable
