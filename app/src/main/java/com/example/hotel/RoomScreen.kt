package com.example.hotel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotel.databinding.RoomScreenBinding


class RoomScreen: Fragment() {

    private var _binding: RoomScreenBinding? = null
    private val binding: RoomScreenBinding
        get() = _binding ?: throw RuntimeException("RoomsScreenBinding == null")



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
        childFragmentManager.beginTransaction()
            .add(R.id.container_tourists_add, TouristsFragment.getInstance(1))
            .commit()

        childFragmentManager.executePendingTransactions()

        binding.svScroll.viewTreeObserver.addOnGlobalLayoutListener {
            binding.svScroll.post {
                binding.svScroll.fullScroll(View.FOCUS_DOWN)
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
}