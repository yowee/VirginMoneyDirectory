package com.virginmoney.virginmoneydirectory.data.model.repository

import com.virginmoney.virginmoneydirectory.data.model.room.RoomModel
import com.virginmoney.virginmoneydirectory.data.remote.APICall
import javax.inject.Inject


class RepositoryImpl @Inject constructor(val apiCall: APICall) : Repository {
    override suspend fun getContacts() = apiCall.getContacts()

    override suspend fun getRooms() : RoomModel= apiCall.getRooms()
}