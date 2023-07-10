package com.virginmoney.virginmoneydirectory.data.repository

import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModel
import com.virginmoney.virginmoneydirectory.data.model.room.RoomModel

interface Repository {

    suspend fun getContacts(): ContactModel
    suspend fun getRooms(): RoomModel
}