package com.example.hotel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotel.R
import com.example.hotel.databinding.FinishScreenBinding


class FinishScreen: Fragment() {

    private var _binding: FinishScreenBinding? = null
    private val binding: FinishScreenBinding
        get() = _binding ?: throw RuntimeException("FinishScreenBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = FinishScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.headerScreen.tvHeader.text = getString(R.string.order_payed)
        binding.headerScreen.backButton.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.btBottom.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container_activity, HotelScreen.getInstance())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = FinishScreen()
    }
}