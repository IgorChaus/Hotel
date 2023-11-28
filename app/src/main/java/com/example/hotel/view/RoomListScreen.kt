package com.example.hotel.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hotel.HotelApp
import com.example.hotel.R
import com.example.hotel.adapter.ContentAdapter
import com.example.hotel.databinding.RoomListScreenBinding
import com.example.hotel.model.Room
import com.example.hotel.viewmodel.RoomListViewModel
import com.example.hotel.viewmodel.RoomListViewModelFactory
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

        val adapter = ContentAdapter{
            showRoom(it)
        }

        binding.rv.adapter = adapter

        viewModel.rooms.observe(viewLifecycleOwner) { rooms ->
            adapter.items = rooms
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

}