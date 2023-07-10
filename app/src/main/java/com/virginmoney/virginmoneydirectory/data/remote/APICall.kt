package com.virginmoney.virginmoneydirectory.data.remote

import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModel
import com.virginmoney.virginmoneydirectory.data.model.room.RoomModel
import retrofit2.http.GET

interface APICall {
    @GET(ApiDetails.CONTACT_END_POINT)
    suspend fun getContacts(): ContactModel

    @GET(ApiDetails.ROOM_END_POINT)
    suspend fun getRooms(): RoomModel
}