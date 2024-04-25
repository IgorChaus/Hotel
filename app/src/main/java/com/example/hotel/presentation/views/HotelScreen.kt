package com.example.hotel.presentation.views

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hotel.R
import com.example.hotel.common.appComponent
import com.example.hotel.databinding.HotelScreenBinding
import com.example.hotel.presentation.adapter.ContentAdapter
import com.example.hotel.presentation.adapter.ViewType
import com.example.hotel.presentation.viewmodels.HotelViewModel
import com.example.hotel.common.BaseFragment
import com.example.hotel.common.repeatOnCreated
import com.example.hotel.common.setPeculiaritiesLayout
import com.example.hotel.presentation.adapter.WrapperPhoto
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HotelScreen: BaseFragment<HotelScreenBinding>(){

    private var hotelName = ""

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HotelViewModel by viewModels { viewModelFactory }

    private val disposables = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
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
        subscribeOnViewModel()
        setupOnClickListeners()
        viewModel.getHotel()
    }

    private fun setupOnClickListeners() {
        binding.btNext.setOnClickListener {
            launchRoomListScreen()
        }

        binding.tvHotelAddress.setOnClickListener { }

        binding.buttonsInfo.buttonComfort.setOnClickListener { }
        binding.buttonsInfo.buttonWhatIncluded.setOnClickListener { }
        binding.buttonsInfo.buttonWhatNoIncluded.setOnClickListener { }
    }

    private fun launchRoomListScreen() {
        val action = HotelScreenDirections.actionHotelScreenToRoomListScreen(hotelName)
        findNavController().navigate(action)
    }

    private fun subscribeOnViewModel() {

        disposables.add(
            viewModel.hotel
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { hotel ->
                    hotelName = hotel.name
                    val wrapperPhotos: List<ViewType> = hotel.imageUrls.map { WrapperPhoto(it) }
                    val adapter = ContentAdapter()
                    adapter.items = wrapperPhotos
                    binding.vpHotel.adapter = adapter
                    TabLayoutMediator(binding.tabLayout, binding.vpHotel) { _, _ ->
                    }.attach()

                    binding.tvHotelName.text = hotel.name
                    binding.tvHotelAddress.text = hotel.address
                    val hotelPrice = String.format("%,d", hotel.minimalPrice)
                        .replace(",", " ")
                    binding.tvPrice.text = getString(R.string.from) + " $hotelPrice" +
                            getString(R.string.rub)
                    binding.tvPriceAbout.text = hotel.priceForIt
                    binding.rating.tvRating.text = hotel.rating.toString()
                    binding.rating.tvRatingName.text = hotel.ratingName
                    binding.tvDescriptionHotel.text = hotel.aboutTheHotel.description

                    setPeculiaritiesLayout(
                        binding.layoutPeculiarities,
                        hotel.aboutTheHotel.peculiarities
                    )
                }
        )

        viewModel.getHotel()

    }

    override fun onDestroy() {
        disposables.dispose()
        viewModel.clearDisposables()
        super.onDestroy()
    }

}