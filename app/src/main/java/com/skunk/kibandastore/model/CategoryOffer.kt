package com.skunk.kibandastore.model

import android.os.Parcel
import android.os.Parcelable

data class CategoryOffer(val name:String?): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryOffer> {
        override fun createFromParcel(parcel: Parcel): CategoryOffer {
            return CategoryOffer(parcel)
        }

        override fun newArray(size: Int): Array<CategoryOffer?> {
            return arrayOfNulls(size)
        }
    }
}