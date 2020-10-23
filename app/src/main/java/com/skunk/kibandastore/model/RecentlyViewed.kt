package com.skunk.kibandastore.model

import android.os.Parcel
import android.os.Parcelable

data class RecentlyViewed(val title:String?,val imageUrl:String?,val description:String?):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(imageUrl)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecentlyViewed> {
        override fun createFromParcel(parcel: Parcel): RecentlyViewed {
            return RecentlyViewed(parcel)
        }

        override fun newArray(size: Int): Array<RecentlyViewed?> {
            return arrayOfNulls(size)
        }
    }
}