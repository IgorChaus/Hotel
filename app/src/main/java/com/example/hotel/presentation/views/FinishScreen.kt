package com.example.hotel.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hotel.R
import com.example.hotel.databinding.FinishScreenBinding
import com.example.hotel.utils.BaseFragment


class FinishScreen: BaseFragment<FinishScreenBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): FinishScreenBinding {
        return FinishScreenBinding.inflate(inflater, container, attachToRoot)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.headerScreen.tvHeader.text = getString(R.string.order_payed)
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        binding.headerScreen.backButton.setOnClickListener {
            goHotelScreen()
        }
        binding.btBottom.setOnClickListener {
            goHotelScreen()
        }
    }

    private fun goHotelScreen() {
        findNavController().popBackStack()
    }

}