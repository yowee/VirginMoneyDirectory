package com.virginmoney.virginmoneydirectory.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.virginmoney.virginmoneydirectory.R
import com.virginmoney.virginmoneydirectory.data.model.room.RoomModel
import com.virginmoney.virginmoneydirectory.data.model.room.RoomModelItemModel
import com.virginmoney.virginmoneydirectory.databinding.ItemRoomBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RoomAdapter(val childFragmentManager: FragmentManager, val roomModel: RoomModel) :
    RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomAdapter.ViewHolder {
        return RoomAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false),
            childFragmentManager
        )
    }

    override fun getItemCount() = roomModel.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        roomModel[position].let { holder.updateUI(it) }
    }



    class ViewHolder(var view: View, private val fragmentManager: FragmentManager) :
        RecyclerView.ViewHolder(view) {
        val binding = ItemRoomBinding.bind(view)

        fun updateUI( room : RoomModelItemModel){
            binding.apply {
                createdAtTextView.text = formatDateString(room.createdAt.toString())
                occupiedTextView.text = if (room.isOccupied == true) "Occupied" else "Not Occupied"
                maxOccupancyTextView.text = "MAX "+ room.maxOccupancy.toString()
                idTextView.text = room.id.toString()
            }


        }

        fun formatDateString(dateString: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd, yyyy - HH:mm", Locale.getDefault())

            try {
                val date: Date = inputFormat.parse(dateString)
                return outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return ""
        }

    }



}
