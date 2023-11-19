package com.example.hotel

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
import com.example.hotel.databinding.MainScreenBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainScreen: Fragment(){
    private var _binding: MainScreenBinding? = null
    private val binding: MainScreenBinding
        get() = _binding ?: throw RuntimeException("MainScreenBinding == null")

    private val hotel = Hotel(
        id = 1,
        name = "Лучший пятизвездочный отель в Хургаде, Египет",
        adress = "Madinat Makadi, Safaga Road, Makadi Bay, Египет",
        minimal_price = 134268,
        price_for_it =  "За тур с перелётом",
        rating = 5,
        rating_name = "Превосходно",
        image_urls = listOf(
            "https://www.atorus.ru/sites/default/files/upload/image/News/56149/Club_Priv%C3%A9_by_Belek_Club_House.jpg",
            "https://deluxe.voyage/useruploads/articles/The_Makadi_Spa_Hotel_02.jpg",
            "https://deluxe.voyage/useruploads/articles/article_1eb0a64d00.jpg"
        ),
        about_the_hotel = Hotel.AboutTheHotel(
            description = "Отель VIP-класса с собственными гольф полями. Высокий уровнь сервиса." +
                    "Рекомендуем для респектабельного отдыха. Отель принимает гостей от 18 лет!",
            peculiarities = listOf(
                "Бесплатный Wifi на всей территории отеля",
                "1 км до пляжа",
                "Бесплатный фитнес-клуб",
                "20 км до аэропорта"
            )
        )
    )

    private val photosHotel = listOf(
        R.drawable.hotel1,
        R.drawable.hotel2,
        R.drawable.hotel3,
        R.drawable.hotel4
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = MainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ImageAdapter(photosHotel)
        binding.vpHotel.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.vpHotel) { tab, position ->
        }.attach()

        binding.tvHotelName.text = hotel.name
        binding.tvHotelAddress.text = hotel.adress
        binding.tvPrice.text = "от ${hotel.minimal_price}"
        binding.tvPriceAbout.text = hotel.price_for_it
        binding.tvRating.text = hotel.rating.toString()
        binding.tvRatingName.text = hotel.rating_name
        binding.tvDescriptionHotel.text = hotel.about_the_hotel.description

        setPeculiaritiesLayout(view)

    }

    private fun setPeculiaritiesLayout(view: View) {
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

                for (peculiarity in hotel.about_the_hotel.peculiarities) {
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

}