package com.example.hotel.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hotel.HotelApp
import com.example.hotel.R
import com.example.hotel.databinding.RoomScreenBinding
import com.example.hotel.utils.NumberTextWatcher
import com.example.hotel.viewmodel.RoomViewModel
import com.example.hotel.viewmodel.RoomViewModelFactory
import javax.inject.Inject

class RoomScreen: Fragment() {

    private var _binding: RoomScreenBinding? = null
    private val binding: RoomScreenBinding
        get() = _binding ?: throw RuntimeException("RoomsScreenBinding == null")

    val component by lazy{
        (requireActivity().application as HotelApp).component
    }

    @Inject
    lateinit var factory: RoomViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[RoomViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = RoomScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.customInfo.etPhone
            .addTextChangedListener(NumberTextWatcher("+7 (***) ***-**-**"))

        binding.headerScreen.tvHeader.text = "Бронирование"

        viewModel.reservation.observe(viewLifecycleOwner){ reservation ->
            binding.hotelInformation.tvHotelName.text = reservation.hotel_name
            binding.hotelInformation.tvHotelAddress.text = reservation.hotel_adress
            binding.hotelInformation.tvRating.text = reservation.horating.toString()
            binding.hotelInformation.tvRatingName.text = reservation.rating_name

            binding.tourInformation.departure.text = reservation.departure
            binding.tourInformation.destinition.text = reservation.arrival_country
            binding.tourInformation.description.text = reservation.room
            binding.tourInformation.dates.text = "${reservation.tour_date_start} " +
                    "- ${reservation.tour_date_stop}"
            binding.tourInformation.hotelName.text = reservation.hotel_name
            binding.tourInformation.nutrition.text = reservation.nutrition
            binding.tourInformation.duration.text = reservation.number_of_nights.toString()

            val tourPrice = String.format("%,d", reservation.tour_price)
                .replace(",", " ") + " ₽"
            binding.totalPrice.tvTourPrice.text = tourPrice
            val fuelPrice = String.format("%,d", reservation.fuel_charge)
                .replace(",", " ") + " ₽"
            binding.totalPrice.tvFuelPrice.text = fuelPrice
            val servicePrice = String.format("%,d", reservation.service_charge)
                .replace(",", " ") + " ₽"
            binding.totalPrice.tvServicePrice.text = servicePrice
            val total = reservation.tour_price + reservation
                .fuel_charge + reservation.service_charge
            val totalString = String.format("%,d", total).replace(",", " ") + " ₽"
            binding.totalPrice.tvTotal.text = totalString
            binding.buttonSuper.text = "Оплатить $totalString"
        }

        val usualColor = ContextCompat.getDrawable(requireContext(),
            R.drawable.rounded_corners_contact
        )
        val errorColor = ContextCompat.getDrawable(requireContext(),
            R.drawable.rounded_corners_error
        )

        viewModel.emailError.observe(viewLifecycleOwner){
            if(it){
                binding.customInfo.containerEmail.background = errorColor
            } else {
                binding.customInfo.containerEmail.background = usualColor
            }
        }

        binding.customInfo.etEmail.setOnFocusChangeListener { viewEditText, hasFocus ->
            if (!hasFocus) {
                val userInput = (viewEditText as EditText).text.toString()
                viewModel.checkEmail(userInput)
            } else {
                viewModel.resetError()
            }
        }

        binding.customInfo.etPhone.setOnFocusChangeListener { viewEditText, hasFocus ->
            if (hasFocus) {
                binding.customInfo.etPhone.setText("+7 (***) ***-**-**")
            }
        }

        if(savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.container_tourists_add, TouristsFragment.getInstance(1))
                .commit()
        }

        binding.headerScreen.backButton.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }


        binding.buttonSuper.setOnClickListener {
            launchFinishScreen()
        }
    }

    private fun launchFinishScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container_activity, FinishScreen.getInstance())
            .addToBackStack(null)
            .commit()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = RoomScreen()
    }
}