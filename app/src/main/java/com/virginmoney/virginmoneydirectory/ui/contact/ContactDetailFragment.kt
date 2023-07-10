package com.virginmoney.virginmoneydirectory.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.virginmoney.virginmoneydirectory.R
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModelItemModel
import com.virginmoney.virginmoneydirectory.databinding.FragmentContactDetail2Binding


class ContactDetailFragment : Fragment() {


    private lateinit var binding: FragmentContactDetail2Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetail2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data passed from the previous fragment or activity
        val contact: ContactModelItemModel? = arguments?.getParcelable("contact")
        if (contact != null) {
            binding.apply {
                nameTextView.text = "${contact.firstName} ${contact.lastName}"
                emailTextView.text = contact.email
                jobTitleTextView.text = contact.jobtitle
                favoriteColorTextView.text = contact.favouriteColor

                Glide.with(requireContext())
                    .load(contact.avatar)
                    .into(binding.avatarImageView)
            }
            // Use the received contact object to display the details
        }
        // Perform any additional setup or logic using the retrieved data
        // For example, you can use the contactId to fetch and display specific contact details
    }
}