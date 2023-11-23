package com.example.hotel.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotel.R
import com.example.hotel.databinding.TouristFragmentBinding

class TouristsFragment: Fragment() {

    private var _binding: TouristFragmentBinding? = null
    private val binding: TouristFragmentBinding
        get() = _binding ?: throw RuntimeException("TouristFragmentBinding == null")

    private val touristsInfoFragment = TouristsInfoFragment.getInstance()
    private var isTouristsShowed = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = TouristFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            val buttonVisibility = savedInstanceState.getInt("buttonVisibility")
            binding.containerButton.visibility = buttonVisibility
            val touristsShowed = savedInstanceState.getBoolean("isTouristsShowed")
            isTouristsShowed = touristsShowed
        }

        val touristsNumber = requireArguments().getInt(KEY_TOURISTS_NUMBER, 1)
        binding.tvTouristNumber.text = "${numberToOrdinalRussian(touristsNumber)} " +
                getString(R.string.tourist)

        childFragmentManager.beginTransaction()
            .add(R.id.container_tourists_info, touristsInfoFragment)
            .commit()
        binding.imageButton.setOnClickListener {
            if(isTouristsShowed){
                childFragmentManager.beginTransaction()
                    .hide(touristsInfoFragment)
                    .commit()
                binding.imageButton.setImageResource(R.drawable.array_top)
                isTouristsShowed = false
            } else {
                childFragmentManager.beginTransaction()
                    .show(touristsInfoFragment)
                    .commit()
                binding.imageButton.setImageResource(R.drawable.array_bottom)
                isTouristsShowed = true
            }
        }
        binding.ibAddTourist.setOnClickListener {
            childFragmentManager.beginTransaction()
                .add(R.id.container_tourists_add, getInstance(touristsNumber + 1))
                .commit()
            binding.containerButton.visibility = View.GONE
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("buttonVisibility", binding.containerButton.visibility)
        outState.putBoolean("isTouristsShowed", isTouristsShowed)
    }

    private fun numberToOrdinalRussian(n: Int): String {
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{

        fun getInstance(touristsNumber: Int): Fragment{
            return TouristsFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_TOURISTS_NUMBER,touristsNumber)
                }
            }
        }
        private const val KEY_TOURISTS_NUMBER = "touristsNumber"
    }
}