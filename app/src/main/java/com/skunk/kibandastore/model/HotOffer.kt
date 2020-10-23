package com.skunk.kibandastore.model

import android.os.Parcel
import android.os.Parcelable

data class HotOffer(val title:String?,val description:String?,val imageUrl:String?):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }
    constructor():this("","","")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HotOffer> {
        override fun createFromParcel(parcel: Parcel): HotOffer {
            return HotOffer(parcel)
        }

        override fun newArray(size: Int): Array<HotOffer?> {
            return arrayOfNulls(size)
        }
    }
}