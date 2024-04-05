package com.example.hotel.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hotel.adapter.ContentAdapter
import com.example.hotel.appComponent
import com.example.hotel.databinding.RoomListScreenBinding
import com.example.hotel.model.Room
import com.example.hotel.utils.BaseFragment
import com.example.hotel.viewmodel.RoomListViewModel
import javax.inject.Inject


class RoomListScreen: BaseFragment<RoomListScreenBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): RoomListScreenBinding {
        return RoomListScreenBinding.inflate(inflater, container, attachToRoot)
    }

    val args by navArgs<RoomListScreenArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: RoomListViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
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

        binding.headerScreen.tvHeader.text = args.hotelName

        binding.headerScreen.backButton.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun showRoom(room: Room) {
        findNavController().navigate(RoomListScreenDirections.actionRoomListScreenToRoomScreen())
    }

}