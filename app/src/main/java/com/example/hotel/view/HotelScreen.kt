package com.example.hotel.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.hotel.HotelApp
import com.example.hotel.R
import com.example.hotel.databinding.HotelScreenBinding
import com.example.hotel.pageradapter.PagerAdapter
import com.example.hotel.viewmodel.HotelViewModel
import com.example.hotel.viewmodel.HotelViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class HotelScreen: Fragment(){
    private var _binding: HotelScreenBinding? = null
    private val binding: HotelScreenBinding
        get() = _binding ?: throw RuntimeException("HotelScreenBinding == null")

    private val component by lazy{
        (requireActivity().application as HotelApp).component
    }

    private var hotelName = ""

    @Inject
    lateinit var factory: HotelViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[HotelViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = HotelScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.hotel.observe(viewLifecycleOwner){ hotel ->
            hotelName = hotel.name
            val adapter = PagerAdapter(hotel.image_urls)
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

            setPeculiaritiesLayout(view, hotel.about_the_hotel.peculiarities)
        }

        binding.btBottom.setOnClickListener {
            launchRoomListScreen()
        }

        requireActivity().supportFragmentManager
            .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    }

    private fun launchRoomListScreen(){
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container_activity, RoomListScreen.getInstance(hotelName))
            .addToBackStack(null)
            .commit()
    }

    private fun setPeculiaritiesLayout(view: View, peculiarities: List<String>) {
        val layoutPeculiarities: LinearLayout = view.findViewById(R.id.layout_peculiarities)

        layoutPeculiarities.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                layoutPeculiarities.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val maxWidth = layoutPeculiarities.width

                var currentLineWidth = 0
                var currentRowLayout = LinearLayout(requireContext())
                currentRowLayout.orientation = LinearLayout.HORIZONTAL
                layoutPeculiarities.addView(currentRowLayout)

                for (peculiarity in peculiarities) {
                    val peculiarityTextView = TextView(requireContext())
                    peculiarityTextView.text = peculiarity
                    val backgroundText = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.rounded_corners_peculiarity
                    )
                    peculiarityTextView.background = backgroundText
                    val colorText = ContextCompat.getColor(requireContext(), R.color.grey_600)
                    peculiarityTextView.setTextColor(colorText)
                    peculiarityTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.toFloat())

                    val densityDpi = resources.displayMetrics.densityDpi
                    val leftPaddingDp = densityDpi / 160 * 10
                    val rightPaddingDp = densityDpi / 160 * 10
                    val topPaddingDp = densityDpi / 160 * 5
                    val bottomPaddingDp = densityDpi / 160 * 5
                    peculiarityTextView.setPadding(
                        leftPaddingDp,
                        topPaddingDp,
                        rightPaddingDp,
                        bottomPaddingDp
                    )

                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    layoutParams.setMargins(8, 8, 8, 8)

                    peculiarityTextView.layoutParams = layoutParams
                    peculiarityTextView.measure(0, 0)

                    if (currentLineWidth + peculiarityTextView.measuredWidth > maxWidth) {
                        currentRowLayout = LinearLayout(requireContext())
                        currentRowLayout.orientation = LinearLayout.HORIZONTAL
                        layoutPeculiarities.addView(currentRowLayout)
                        currentLineWidth = 0
                    }

                    currentRowLayout.addView(peculiarityTextView)
                    currentLineWidth += peculiarityTextView.measuredWidth
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance() = HotelScreen()
    }

}