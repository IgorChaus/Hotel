package com.example.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotel.databinding.TouristAddBinding

class TouristAdd: Fragment() {
    private var _binding: TouristAddBinding? = null
    private val binding: TouristAddBinding
        get() = _binding ?: throw RuntimeException("TouristAddBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = TouristAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.ibAddTourist.setOnClickListener {
//            childFragmentManager.beginTransaction()
//                .add(R.id.container_tourists_add, TouristsFragment.getInstance())
//                .commit()
//        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}