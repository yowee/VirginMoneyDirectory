
package com.virginmoney.virginmoneydirectory.ui.contact


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.virginmoney.virginmoneydirectory.R
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModel
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModelItemModel
import com.virginmoney.virginmoneydirectory.databinding.FragmentContactBinding
import com.virginmoney.virginmoneydirectory.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactFragment : Fragment(){
    private lateinit var binding: FragmentContactBinding
    private val viewModel: ContactViewModel by viewModels()
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        loadData()
//        observeContactData()
//        if (!viewModel.isLoaded)
//            viewModel.getArticleData()
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contact_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Handle search query submission
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Handle search query text change
                filterData(newText)
                return true
            }
        })
    }

    private fun setupRecyclerView(contacts: ContactModel) {
        contactAdapter = ContactAdapter(contacts){ clickedContact ->
            openDetailView(clickedContact)
        }
        binding.rvContacts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactAdapter
        }
    }


    private fun loadData(contact: ContactModel) {
        setupRecyclerView(contact)
    }
    private fun observeContactData() {
        viewModel.contactLiveData.observe(viewLifecycleOwner) { contactData ->
            contactAdapter.filterList(contactData)
        }
    }

    private fun filterData(query: String) {
        val contactData = viewModel.contactLiveData.value
        val filteredList = contactData?.filter { contact ->
            contact.firstName?.contains(query, ignoreCase = true) == true ||
                    contact.lastName?.contains(query, ignoreCase = true) == true
        }
        val filteredContactModel = ContactModel()
        filteredContactModel.addAll(filteredList.orEmpty())
        contactAdapter.filterList(filteredContactModel)

    }

    private fun openDetailView(clickedContact: ContactModelItemModel) {
        val bundle = Bundle().apply {
            putParcelable("contact", clickedContact) // Pass the selected contact to the DetailFragment
        }
        findNavController()
            .navigate(
                R.id.contact_to_details,
                bundle
            )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sign_out -> {
                // Handle sign out
                val firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.signOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)

                Toast.makeText(requireContext(), "Signed out Successfully", Toast.LENGTH_LONG)
                requireActivity().finish()
                return true
            }
            // Handle other menu items if needed
        }
        return super.onOptionsItemSelected(item)
    }
}
