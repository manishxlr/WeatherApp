package com.example.sampleapplication.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Wind(
    val speed: Double,
    val deg : Int
) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeValue(speed)
        p0?.writeValue(deg)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Wind> {
        override fun createFromParcel(parcel: Parcel): Wind {
            return Wind(parcel)
        }

        override fun newArray(size: Int): Array<Wind?> {
            return arrayOfNulls(size)
        }
    }

}