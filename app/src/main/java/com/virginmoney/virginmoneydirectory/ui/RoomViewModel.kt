package com.virginmoney.virginmoneydirectory.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModel
import com.virginmoney.virginmoneydirectory.data.repository.Repository
import com.virginmoney.virginmoneydirectory.data.model.room.RoomModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(val repository: Repository): ViewModel() {


    val roomLiveData: MutableLiveData<RoomModel> by lazy{
        MutableLiveData<RoomModel>()
    }
    var isLoaded: Boolean = false

    fun getRoomData() {
        CoroutineScope(Dispatchers.Main).launch {
            val roomModel = repository.getRooms()
            roomLiveData.postValue(roomModel)
            isLoaded = true
        }
    }
}