package com.example.hotel.presentation.views

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hotel.R
import com.example.hotel.presentation.adapter.ExpandableListAdapter
import com.example.hotel.common.appComponent
import com.example.hotel.databinding.RoomScreenBinding
import com.example.hotel.common.BaseFragment
import com.example.hotel.presentation.viewmodels.RoomViewModel
import com.example.hotel.domain.models.TouristData
import com.example.hotel.common.repeatOnCreated
import javax.inject.Inject

class RoomScreen: BaseFragment<RoomScreenBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): RoomScreenBinding {
        return RoomScreenBinding.inflate(inflater, container, attachToRoot)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: RoomViewModel by viewModels { viewModelFactory }

    private lateinit var expandableListAdapter: ExpandableListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.headerScreen.tvHeader.text = getString(R.string.reservation)
        setExpandableListView()
        setReservationObserver()
        setShowEmptyFieldsObserver()
        restoreDataIfFragmentRecreated(savedInstanceState)
        setEmailFieldHandlers()
        setAddButtonListener()
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        binding.buttonPay.setOnClickListener {
            if (!viewModel.isAnyFieldEmpty(expandableListAdapter.touristList)) {
                launchFinishScreen()
            }
        }

        binding.headerScreen.backButton.setOnClickListener {
            goBack()
        }
    }

    private fun restoreDataIfFragmentRecreated(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val touristData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getParcelable(KEY_TOURIST_LIST, TouristData::class.java)
            } else {
                savedInstanceState.getParcelable(KEY_TOURIST_LIST)
            }
            expandableListAdapter.touristList = touristData?.touristList ?: mutableMapOf()
            setDataExpandableListAdapter()

            val phone = savedInstanceState.getString(KEY_PHONE, MASK)
            binding.customInfo.etEmail.setText(savedInstanceState.getString(KEY_EMAIL))
            binding.customInfo.etPhone
                .addTextChangedListener(NumberTextWatcher(phone))
            binding.customInfo.etPhone.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.customInfo.etPhone.setText(phone)
                }
            }
        } else {
            binding.customInfo.etPhone
                .addTextChangedListener(NumberTextWatcher(MASK))
            binding.customInfo.etPhone.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus && binding.customInfo.etPhone.text!!.isEmpty()) {
                    binding.customInfo.etPhone.setText(MASK)
                }
            }
        }
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

    private fun setExpandableListView() {
        expandableListAdapter = ExpandableListAdapter(
            binding.root.context,
            viewModel.listTouristGroups,
            viewModel.listTouristsChild
        )
        binding.touristInfo.expandableListView.setAdapter(expandableListAdapter)
    }

    private fun setAddButtonListener() {
        binding.btAddTourist.setOnClickListener {
            viewModel.addNewTourist()
            setDataExpandableListAdapter()
            binding.touristInfo.expandableListView
                .expandGroup(viewModel.listTouristGroups.size - 1)
        }
    }

    private fun setEmailFieldHandlers() {
        val usualColor = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.rounded_corners_contact
        )
        val errorColor = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.rounded_corners_error
        )

        viewModel.emailError.repeatOnCreated(this) {
            if (it) {
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
    }

    private fun setShowEmptyFieldsObserver() {
        viewModel.showEmptyFields.repeatOnCreated(this) {
            if (it) {
                expandableListAdapter.isShowError = true
                expandableListAdapter.notifyDataSetChanged()
                val expandableListView = binding.touristInfo.expandableListView
                for (i in 0 until expandableListView.expandableListAdapter.groupCount) {
                    expandableListView.expandGroup(i)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setReservationObserver() {
        viewModel.reservation.repeatOnCreated(this) { reservation ->
            binding.hotelInformation.tvHotelName.text = reservation.hotelName
            binding.hotelInformation.tvHotelAddress.text = reservation.hotelAddress
            binding.hotelInformation.rating.tvRating.text = reservation.horating.toString()
            binding.hotelInformation.rating.tvRatingName.text = reservation.ratingName

            binding.tourInformation.departure.text = reservation.departure
            binding.tourInformation.destinition.text = reservation.arrivalCountry
            binding.tourInformation.description.text = reservation.room
            binding.tourInformation.dates.text = "${reservation.tourDateStart} " +
                    "- ${reservation.tourDateStop}"
            binding.tourInformation.hotelName.text = reservation.hotelName
            binding.tourInformation.nutrition.text = reservation.nutrition
            binding.tourInformation.duration.text = reservation.numberOfNights.toString()

            val tourPrice = String.format("%,d", reservation.tourPrice)
                .replace(",", " ") + getString(R.string.rub)
            binding.totalPrice.tvTourPrice.text = tourPrice
            val fuelPrice = String.format("%,d", reservation.fuelCharge)
                .replace(",", " ") + getString(R.string.rub)
            binding.totalPrice.tvFuelPrice.text = fuelPrice
            val servicePrice = String.format("%,d", reservation.serviceCharge)
                .replace(",", " ") + getString(R.string.rub)
            binding.totalPrice.tvServicePrice.text = servicePrice
            val total = reservation.tourPrice + reservation
                .fuelCharge + reservation.serviceCharge
            val totalString = String.format("%,d", total).replace(",", " ") +
                    getString(R.string.rub)
            binding.totalPrice.tvTotal.text = totalString
            binding.buttonPay.text = getString(R.string.pay) + " $totalString"

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val touristData = TouristData(
            expandableListAdapter.touristList
        )
        outState.putParcelable(KEY_TOURIST_LIST, touristData)
        outState.putString(KEY_PHONE, binding.customInfo.etPhone.text.toString())
        outState.putString(KEY_EMAIL, binding.customInfo.etEmail.text.toString())
    }

    private fun setDataExpandableListAdapter() {
        expandableListAdapter.listTouristGroup = viewModel.listTouristGroups
        expandableListAdapter.listTouristChild = viewModel.listTouristsChild
        expandableListAdapter.notifyDataSetChanged()
    }

    private fun launchFinishScreen() {
        findNavController().navigate(RoomScreenDirections.actionRoomScreenToFinishScreen())
    }

    companion object{
        const val MASK = "+7 (***) ***-**-**"
        const val KEY_PHONE = "Phone"
        const val KEY_EMAIL = "Email"
        const val KEY_TOURIST_LIST = "TouristList"
    }
}