package com.example.hotel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import com.example.hotel.databinding.RoomScreenBinding
import com.example.hotel.model.Reservation


class RoomScreen: Fragment() {

    private var _binding: RoomScreenBinding? = null
    private val binding: RoomScreenBinding
        get() = _binding ?: throw RuntimeException("RoomsScreenBinding == null")

    val reservation = Reservation(
        id = 1,
        hotel_name = "Лучший пятизвездочный отель в Хургаде, Египет",
        hotel_adress = "Madinat Makadi, Safaga Road, Makadi Bay, Египет",
        horating = 5,
        rating_name = "Превосходно",
        departure = "Москва",
        arrival_country = "Египет, Хургада",
        tour_date_start = "19.09.2023",
        tour_date_stop = "27.09.2023",
        number_of_nights = 7,
        room = "Люкс номер с видом на море",
        nutrition = "Все включено",
        tour_price = 289400,
        fuel_charge = 9300,
        service_charge = 2150
    )



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = RoomScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.customInfo.etPhone.addTextChangedListener(NumberTextWatcher("(###) ###-##-##"))
        if(savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.container_tourists_add, TouristsFragment.getInstance(1), "MyFragment")
                .commit()

            childFragmentManager.executePendingTransactions()

            val touristsFragment =
                childFragmentManager.findFragmentByTag("MyFragment") as? TouristsFragment
            touristsFragment?.scrollListener = {
                binding.svScroll.viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        binding.svScroll.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        binding.svScroll.post {
                            binding.svScroll.fullScroll(View.FOCUS_DOWN)
                        }
                    }
                })
            }
        }

    }


    fun numberToOrdinalRussian(n: Int): String {
        val numbers = listOf("Первый", "Второй", "Третий", "Четвертый", "Пятый", "Шестой", "Седьмой", "Восьмой", "Девятый", "Десятый")
        return numbers[n-1]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = RoomScreen()
    }
}