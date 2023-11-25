package com.example.hotel.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hotel.HotelApp
import com.example.hotel.R
import com.example.hotel.adapter.ExpandableListAdapter
import com.example.hotel.databinding.RoomScreenBinding
import com.example.hotel.utils.NumberTextWatcher
import com.example.hotel.viewmodel.RoomViewModel
import com.example.hotel.viewmodel.RoomViewModelFactory
import javax.inject.Inject

class RoomScreen: Fragment() {

    private var _binding: RoomScreenBinding? = null
    private val binding: RoomScreenBinding
        get() = _binding ?: throw RuntimeException("RoomsScreenBinding == null")

    private val component by lazy{
        (requireActivity().application as HotelApp).component
    }

    @Inject
    lateinit var factory: RoomViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[RoomViewModel::class.java]
    }

    lateinit var expandableListAdapter: ExpandableListAdapter

    val listTouristGroups = arrayListOf("Первый турист")
    val touristInfo = listOf(
        "Имя",
        "Фамилия",
        "Дата рождения",
        "Гражданство",
        "Номер загранпаспорта",
        "Срок действия загранпаспорта"
    )
    val listTouristsChild = hashMapOf(
        "Первый турист" to touristInfo,
    )



    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
        expandableListAdapter = ExpandableListAdapter(context, listTouristGroups, listTouristsChild)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = RoomScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customInfo.etPhone
            .addTextChangedListener(NumberTextWatcher(MASK))

        binding.headerScreen.tvHeader.text = getString(R.string.reservation)

        viewModel.reservation.observe(viewLifecycleOwner){ reservation ->
            binding.hotelInformation.tvHotelName.text = reservation.hotel_name
            binding.hotelInformation.tvHotelAddress.text = reservation.hotel_adress
            binding.hotelInformation.rating.tvRating.text = reservation.horating.toString()
            binding.hotelInformation.rating.tvRatingName.text = reservation.rating_name

            binding.tourInformation.departure.text = reservation.departure
            binding.tourInformation.destinition.text = reservation.arrival_country
            binding.tourInformation.description.text = reservation.room
            binding.tourInformation.dates.text = "${reservation.tour_date_start} " +
                    "- ${reservation.tour_date_stop}"
            binding.tourInformation.hotelName.text = reservation.hotel_name
            binding.tourInformation.nutrition.text = reservation.nutrition
            binding.tourInformation.duration.text = reservation.number_of_nights.toString()

            val tourPrice = String.format("%,d", reservation.tour_price)
                .replace(",", " ") + getString(R.string.rub)
            binding.totalPrice.tvTourPrice.text = tourPrice
            val fuelPrice = String.format("%,d", reservation.fuel_charge)
                .replace(",", " ") + getString(R.string.rub)
            binding.totalPrice.tvFuelPrice.text = fuelPrice
            val servicePrice = String.format("%,d", reservation.service_charge)
                .replace(",", " ") + getString(R.string.rub)
            binding.totalPrice.tvServicePrice.text = servicePrice
            val total = reservation.tour_price + reservation
                .fuel_charge + reservation.service_charge
            val totalString = String.format("%,d", total).replace(",", " ") +
                    getString(R.string.rub)
            binding.totalPrice.tvTotal.text = totalString
            binding.buttonPay.text = getString(R.string.pay) + " $totalString"
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

        binding.customInfo.etPhone.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.customInfo.etPhone.setText(MASK)
            }
        }

        binding.touristInfo.expandableListView.setAdapter(expandableListAdapter)

        binding.btAddTourist.setOnClickListener {
            addNewTourist()
        }

        binding.headerScreen.backButton.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.buttonPay.setOnClickListener {
            if(!checkEmptyInfo(expandableListAdapter.touristList)){
                Log.i("MyTag","hhhhhh")
                expandableListAdapter.isShowError = true
                expandableListAdapter.notifyDataSetChanged()
                val expandableListView = binding.touristInfo.expandableListView
                for (i in 0 until expandableListView.expandableListAdapter.groupCount) {
                    expandableListView.expandGroup(i)
                }
            }else {
                launchFinishScreen()
            }

        }
    }

    private fun checkEmptyInfo(touristList: MutableMap<Pair<Int, Int>, String>): Boolean {
        for (i in 0 until  listTouristGroups.size){
            for (j in 0 .. 5){
                if(touristList[Pair(i, j)] == null) {
                    return false
                }
            }
        }
        return true
    }

    private fun addNewTourist(){
        val numberTourist = listTouristGroups.size + 1
        val numberTouristString = numberToOrdinal(numberTourist)
        listTouristGroups.add(numberTouristString)
        listTouristsChild[numberTouristString] = touristInfo
        expandableListAdapter.listTouristGroup = listTouristGroups
        expandableListAdapter.listTouristChild = listTouristsChild
        expandableListAdapter.notifyDataSetChanged()
    }

    private fun numberToOrdinal(n: Int): String {
        val numbers = listOf(
            getString(R.string.first),
            getString(R.string.second),
            getString(R.string.third),
            getString(R.string.fourth),
            getString(R.string.fifth),
            getString(R.string.sixth),
            getString(R.string.seventh),
            getString(R.string.eighth),
            getString(R.string.ninth),
            getString(R.string.tenth)
        )
        return numbers[n-1]
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
        const val MASK = "+7 (***) ***-**-**"
    }
}