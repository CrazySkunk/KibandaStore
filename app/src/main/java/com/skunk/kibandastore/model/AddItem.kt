package com.skunk.kibandastore.model

import android.os.Parcel
import android.os.Parcelable

data class AddItem (val imageUrl:String?,val title:String?,val description:String?,val pricePerUnit:String?,val quantity:String?,val categoryName:String?):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }
    constructor():this("","","","","","")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageUrl)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(pricePerUnit)
        parcel.writeString(quantity)
        parcel.writeString(categoryName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddItem> {
        override fun createFromParcel(parcel: Parcel): AddItem {
            return AddItem(parcel)
        }

        override fun newArray(size: Int): Array<AddItem?> {
            return arrayOfNulls(size)
        }
    }
}