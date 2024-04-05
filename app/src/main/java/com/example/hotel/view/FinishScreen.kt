package com.example.hotel.view

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
        binding.headerScreen.backButton.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.btBottom.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}