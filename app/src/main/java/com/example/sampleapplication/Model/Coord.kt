package com.example.sampleapplication.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Coord (
    val lon: Double,
    val lat : Double
) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeValue(lon)
        p0?.writeValue(lat)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coord> {
        override fun createFromParcel(parcel: Parcel): Coord {
            return Coord(parcel)
        }

        override fun newArray(size: Int): Array<Coord?> {
            return arrayOfNulls(size)
        }
    }

}