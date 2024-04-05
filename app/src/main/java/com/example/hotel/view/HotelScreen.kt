package com.example.hotel.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hotel.HotelApp
import com.example.hotel.R
import com.example.hotel.adapter.ContentAdapter
import com.example.hotel.adapter.ViewType
import com.example.hotel.databinding.HotelScreenBinding
import com.example.hotel.utils.BaseFragment
import com.example.hotel.utils.setPeculiaritiesLayout
import com.example.hotel.viewmodel.HotelViewModel
import com.example.hotel.viewmodel.ViewModelFactory
import com.example.hotel.wrappers.WrapperPhoto
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class HotelScreen: BaseFragment<HotelScreenBinding>(){

    private val component by lazy{
        (requireActivity().application as HotelApp).component
    }

    private var hotelName = ""

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[HotelViewModel::class.java]
    }


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): HotelScreenBinding {
        return HotelScreenBinding.inflate(inflater, container, attachToRoot)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.hotel.observe(viewLifecycleOwner){ hotel ->
            hotelName = hotel.name
            val wrapperPhotos: List<ViewType> = hotel.image_urls.map { WrapperPhoto(it) }
            val adapter = ContentAdapter()
            adapter.items = wrapperPhotos
            binding.vpHotel.adapter = adapter
            TabLayoutMediator(binding.tabLayout, binding.vpHotel) { _, _ ->
            }.attach()

            binding.tvHotelName.text = hotel.name
            binding.tvHotelAddress.text = hotel.adress
            val hotelPrice = String.format("%,d", hotel.minimal_price)
                .replace(",", " ")
            binding.tvPrice.text = getString(R.string.from) +" $hotelPrice" +
                    getString(R.string.rub)
            binding.tvPriceAbout.text = hotel.price_for_it
            binding.rating.tvRating.text = hotel.rating.toString()
            binding.rating.tvRatingName.text = hotel.rating_name
            binding.tvDescriptionHotel.text = hotel.about_the_hotel.description

            setPeculiaritiesLayout(
                binding.layoutPeculiarities,
                hotel.about_the_hotel.peculiarities
            )
        }

        binding.btBottom.setOnClickListener {
            findNavController().navigate(
                HotelScreenDirections.actionHotelScreenToRoomListScreen(hotelName
                )
            )
        }

        binding.tvHotelAddress.setOnClickListener {  }

        binding.buttonsInfo.buttonComfort.setOnClickListener {  }
        binding.buttonsInfo.buttonWhatIncluded.setOnClickListener {  }
        binding.buttonsInfo.buttonWhatNoIncluded.setOnClickListener {  }

        requireActivity().supportFragmentManager
            .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    }

}