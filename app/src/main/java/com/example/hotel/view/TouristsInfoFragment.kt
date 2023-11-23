package com.example.hotel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotel.databinding.TouristsInfoBinding

class TouristsInfoFragment: Fragment() {

    private var _binding: TouristsInfoBinding? = null
    private val binding: TouristsInfoBinding
        get() = _binding ?: throw RuntimeException("TouristsInfoBinding == null")



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = TouristsInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = TouristsInfoFragment()
    }
}