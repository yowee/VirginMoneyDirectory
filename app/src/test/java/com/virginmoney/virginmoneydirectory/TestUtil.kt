package com.virginmoney.virginmoneydirectory

import com.virginmoney.virginmoneydirectory.data.model.contact.ContactModelItemModel
import com.virginmoney.virginmoneydirectory.data.model.room.RoomModelItemModel

class TestUtil {
    companion object {
        fun getDummyContactModel() = ContactModelItemModel(
            avatar = "https://randomuser.me/api/portraits/women/21.jpg",
            createdAt = "2022-01-24T17:02:23.729Z",
            data = null,
            email = "Crystel.Nicolas61@hotmail.com",
            favouriteColor = "pink",
            firstName = "Maggie",
            fromName = "2",
            id = "1",
            jobtitle = "Future Functionality Strategist",
            lastName = "Brekke",
            name = "jhon doe ",
            size = "2",
            to = "to",
            type = "type"
        )

        fun getDummyRoomModel() = RoomModelItemModel(
            createdAt = "2022-01-24T20:52:50.765Z",
            id = "1",
            isOccupied = false,
            maxOccupancy = 53539
        )


    }
}