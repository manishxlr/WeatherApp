package com.example.sampleapplication.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Main(
    val temp: Double,
    val feels_like : Double,
    val temp_min : Double,
    val temp_max : Double,
    val pressure : Int,
    val humidity : Int
) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeValue(temp)
        p0?.writeValue(feels_like)
        p0?.writeValue(temp_min)
        p0?.writeValue(temp_max)
        p0?.writeValue(pressure)
        p0?.writeValue(humidity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Main> {
        override fun createFromParcel(parcel: Parcel): Main {
            return Main(parcel)
        }

        override fun newArray(size: Int): Array<Main?> {
            return arrayOfNulls(size)
        }
    }

}