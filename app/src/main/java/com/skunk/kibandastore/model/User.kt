package com.skunk.kibandastore.model

import android.os.Parcel
import android.os.Parcelable

data class User (val uid:String?,val names:String?,val email:String?,val phoneNo:String?,val imageUrl:String?,val location:String?):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(names)
        parcel.writeString(email)
        parcel.writeString(phoneNo)
        parcel.writeString(imageUrl)
        parcel.writeString(location)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}