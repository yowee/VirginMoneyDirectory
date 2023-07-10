package com.virginmoney.virginmoneydirectory.data.model.contact


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ContactModelItemModel(
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("data")
    val `data`: DataModel?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("favouriteColor")
    val favouriteColor: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("fromName")
    val fromName: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("jobtitle")
    val jobtitle: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("size")
    val size: String?,
    @SerializedName("to")
    val to: String?,
    @SerializedName("type")
    val type: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(DataModel::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(avatar)
        parcel.writeString(createdAt)
        parcel.writeParcelable(data, flags)
        parcel.writeString(email)
        parcel.writeString(favouriteColor)
        parcel.writeString(firstName)
        parcel.writeString(fromName)
        parcel.writeString(id)
        parcel.writeString(jobtitle)
        parcel.writeString(lastName)
        parcel.writeString(name)
        parcel.writeString(size)
        parcel.writeString(to)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContactModelItemModel> {
        override fun createFromParcel(parcel: Parcel): ContactModelItemModel {
            return ContactModelItemModel(parcel)
        }

        override fun newArray(size: Int): Array<ContactModelItemModel?> {
            return arrayOfNulls(size)
        }
    }
}