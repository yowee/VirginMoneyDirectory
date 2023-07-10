package com.virginmoney.virginmoneydirectory.data.model.room


import com.google.gson.annotations.SerializedName

data class RoomModelItemModel(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("isOccupied")
    val isOccupied: Boolean?,
    @SerializedName("maxOccupancy")
    val maxOccupancy: Int?
)