package com.example.sampleapplication.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Weather(
    val id: Int,
    val main : String,
    val description : String,
    val icon : String
) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeValue(id)
        p0?.writeValue(main)
        p0?.writeValue(description)
        p0?.writeValue(icon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }

}