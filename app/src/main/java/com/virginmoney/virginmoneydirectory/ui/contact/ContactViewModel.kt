package com.virginmoney.virginmoneydirectory.ui.contact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModel
import com.virginmoney.virginmoneydirectory.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(val repository : Repository) : ViewModel(){


    val contactLiveData: MutableLiveData<ContactModel> by lazy{
        MutableLiveData<ContactModel>()
    }
    var isLoaded: Boolean = false

    fun getArticleData()  {
        CoroutineScope(Dispatchers.Main).launch {
            val contacts = repository.getContacts()
            contactLiveData.postValue(contacts)
            isLoaded = true
        }
    }
}