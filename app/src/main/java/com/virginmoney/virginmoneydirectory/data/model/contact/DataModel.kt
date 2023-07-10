package com.virginmoney.virginmoneydirectory.data.model.contact


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DataModel(
    @SerializedName("body")
    val body: String?,
    @SerializedName("fromId")
    val fromId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("meetingid")
    val meetingid: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("toId")
    val toId: String?
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(body)
        parcel.writeString(fromId)
        parcel.writeString(id)
        parcel.writeString(meetingid)
        parcel.writeString(title)
        parcel.writeString(toId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataModel> {
        override fun createFromParcel(parcel: Parcel): DataModel {
            return DataModel(parcel)
        }

        override fun newArray(size: Int): Array<DataModel?> {
            return arrayOfNulls(size)
        }
    }
}