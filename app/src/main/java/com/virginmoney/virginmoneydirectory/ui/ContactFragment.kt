package com.virginmoney.virginmoneydirectory.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.virginmoney.virginmoneydirectory.R
import com.virginmoney.virginmoneydirectory.data.ContactViewModel
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModel
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModelItemModel
import com.virginmoney.virginmoneydirectory.databinding.FragmentContactBinding
import com.virginmoney.virginmoneydirectory.databinding.FragmentContactDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactFragment : Fragment() {
    lateinit var binding: FragmentContactBinding

    companion object {
        fun newInstance() = ContactFragment()
    }

    private lateinit var viewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentContactBinding.inflate(inflater, container, false)

        val viewModel by viewModels<ContactViewModel>()

        if (!viewModel.isLoaded)
            viewModel.getArticleData()

        viewModel.contactLiveData.observe(viewLifecycleOwner) { articleData ->
            loadData(articleData)
        }

        return binding.root

    }


    private fun loadData(contact : ContactModel) {

        binding.rvContacts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ContactAdapter(childFragmentManager, contact) { clickedContact ->
                // Handle item click here
                openDetailView(clickedContact)
            }
        }

    }

    private fun openDetailView(clickedContact: ContactModelItemModel) {
        val detailFragment = DetailFragment()
        val bundle = Bundle().apply {
            putParcelable("contact", clickedContact) // Pass the selected contact to the DetailFragment
        }
        detailFragment.arguments = bundle

        // Perform fragment transaction to show the DetailFragment
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, detailFragment)
            .addToBackStack(null)
            .commit()
    }

}


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailBinding.inflate(inflater, container, false)
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
