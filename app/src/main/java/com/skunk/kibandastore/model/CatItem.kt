package com.skunk.kibandastore.model

import android.os.Parcel
import android.os.Parcelable

data class CatItem (val image:Int?,val name:String?):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString()) {
    }
    constructor():this(0,"")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(image)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CatItem> {
        override fun createFromParcel(parcel: Parcel): CatItem {
            return CatItem(parcel)
        }

        override fun newArray(size: Int): Array<CatItem?> {
            return arrayOfNulls(size)
        }
    }
}