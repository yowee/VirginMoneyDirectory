package com.virginmoney.virginmoneydirectory.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.virginmoney.virginmoneydirectory.data.model.room.RoomModel
import com.virginmoney.virginmoneydirectory.databinding.FragmentRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomFragment : Fragment() {

    lateinit var binding: FragmentRoomBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRoomBinding.inflate(inflater, container, false)

        val viewModel by viewModels<RoomViewModel>()

        if (!viewModel.isLoaded)
            viewModel.getRoomData()

        viewModel.roomLiveData.observe(viewLifecycleOwner) {
            loadData(it)
        }

        return binding.root

    }

    private fun loadData(roomModel: RoomModel ) {

        binding.rvRooms.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RoomAdapter(childFragmentManager, roomModel)
        }

    }



}