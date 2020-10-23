package com.skunk.kibandastore.model

import android.os.Parcel
import android.os.Parcelable

data class CheckOutRowItem (val index:Int?,val name:String?,val quantity:Int?,val price:Int?):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(index)
        parcel.writeString(name)
        parcel.writeValue(quantity)
        parcel.writeValue(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CheckOutRowItem> {
        override fun createFromParcel(parcel: Parcel): CheckOutRowItem {
            return CheckOutRowItem(parcel)
        }

        override fun newArray(size: Int): Array<CheckOutRowItem?> {
            return arrayOfNulls(size)
        }
    }
}