package com.example.hotel.view

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
import androidx.lifecycle.ViewModelProvider
import com.elveum.elementadapter.simpleAdapter
import com.example.hotel.HotelApp
import com.example.hotel.R
import com.example.hotel.adapter.PagerAdapter
import com.example.hotel.databinding.RoomListScreenBinding
import com.example.hotel.databinding.ItemRoomBinding
import com.example.hotel.model.Room
import com.example.hotel.viewmodel.RoomListViewModel
import com.example.hotel.viewmodel.RoomListViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject


class RoomListScreen: Fragment() {

    private var _binding: RoomListScreenBinding? = null
    private val binding: RoomListScreenBinding
        get() = _binding ?: throw RuntimeException("RoomsListScreenBinding == null")

    @Inject
    lateinit var factory: RoomListViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), factory)[RoomListViewModel::class.java]
    }

    private val component by lazy{
        (requireActivity().application as HotelApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = RoomListScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = simpleAdapter<Room, ItemRoomBinding> {
            areItemsSame = { oldRoom, newRoom -> oldRoom.id == newRoom.id }
            bind { room ->
                roomName.text = room.name
                val price = String.format("%,d", room.price).replace(",", " ") +
                        roomName.context.getString(R.string.rub)
                roomPrice.text = price
                pricePer.text = room.price_per
                val adapter = PagerAdapter(room.image_urls)
                vpHotel.adapter = adapter
                TabLayoutMediator(tabLayout, vpHotel) { _, _ ->
                }.attach()
                setPeculiaritiesLayout(peculiaritiesLayout, room.peculiarities)
            }
            listeners {
                btChooseRoom.onClick { room ->
                    showRoom(room)
                }
            }
        }

        binding.rv.adapter = adapter

        viewModel.rooms.observe(viewLifecycleOwner) { rooms ->
            adapter.submitList(rooms)
        }

        val hotelName = requireArguments().getString(KEY_HOTEL, "")
        binding.headerScreen.tvHeader.text = hotelName

        binding.headerScreen.backButton.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance(hotelName: String): Fragment{
            return RoomListScreen().apply {
                arguments = Bundle().apply {
                    putString(KEY_HOTEL,hotelName)
                }
            }
        }

        const val KEY_HOTEL = "Hotel name"
    }

    private fun showRoom(room: Room) {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container_activity, RoomScreen.getInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun setPeculiaritiesLayout(
        layoutPeculiarities: LinearLayout,
        peculiarities: List<String>
    ) {
        layoutPeculiarities.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                layoutPeculiarities.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val maxWidth = layoutPeculiarities.width

                var currentLineWidth = 0
                var currentRowLayout = LinearLayout(layoutPeculiarities.context)
                currentRowLayout.orientation = LinearLayout.HORIZONTAL
                layoutPeculiarities.addView(currentRowLayout)

                for (peculiarity in peculiarities) {
                    val peculiarityTextView = TextView(layoutPeculiarities.context)
                    peculiarityTextView.text = peculiarity
                    val backgroundText = ContextCompat.getDrawable(
                        layoutPeculiarities.context,
                        R.drawable.rounded_corners_peculiarity
                    )
                    peculiarityTextView.background = backgroundText
                    val colorText = ContextCompat.getColor(
                        layoutPeculiarities.context,
                        R.color.grey_600
                    )
                    peculiarityTextView.setTextColor(colorText)
                    peculiarityTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.toFloat())

                    val densityDpi = layoutPeculiarities.resources.displayMetrics.densityDpi
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
                        currentRowLayout = LinearLayout(layoutPeculiarities.context)
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

}