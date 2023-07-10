package com.virginmoney.virginmoneydirectory.ui.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.virginmoney.virginmoneydirectory.R
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModel
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModelItemModel
import com.virginmoney.virginmoneydirectory.databinding.ItemContactBinding

class ContactAdapter(
    var contacts: ContactModel,
    private val onItemClick: (ContactModelItemModel) -> Unit
) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false),
           onItemClick
        )
    }


    override fun getItemCount(): Int = contacts.size

    fun filterList(filteredList: ContactModel) {
        contacts = filteredList
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        contacts[position].let { holder.updateUI(it) }
    }

    class ViewHolder(
        var view: View,
        val onItemClick: (ContactModelItemModel) -> Unit
    ) :
        RecyclerView.ViewHolder(view) {
        val binding = ItemContactBinding.bind(view)

        fun updateUI(contact: ContactModelItemModel) {
            binding.apply {
                Glide.with(view)
                    .load(contact.avatar)
                    .placeholder(R.drawable.person) // Set the placeholder image resource
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(avatarImageView)
                firstNameTextView.text = contact.firstName
//                lastNameTextView.text = contact.lastName
                emailTextView.text = contact.email
                jobTitleTextView.text = contact.jobtitle
//                favoriteColorTextView.text = contact.favouriteColor
            }

            itemView.setOnClickListener {
                onItemClick(contact) // Invoke the item click listener
            }

        }

    }

}
