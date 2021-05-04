package com.example.sampleapplication.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Sys (
    val type: Int,
    val id : Int,
    val country : String,
    val sunrise : Int,
    val sunset : Int
) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeValue(type)
        p0?.writeValue(id)
        p0?.writeValue(country)
        p0?.writeValue(sunrise)
        p0?.writeValue(sunset)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sys> {
        override fun createFromParcel(parcel: Parcel): Sys {
            return Sys(parcel)
        }

        override fun newArray(size: Int): Array<Sys?> {
            return arrayOfNulls(size)
        }
    }

}