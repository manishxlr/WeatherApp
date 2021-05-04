package com.example.sampleapplication.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Clouds(
    val all: Int
) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeValue(all)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Clouds> {
        override fun createFromParcel(parcel: Parcel): Clouds {
            return Clouds(parcel)
        }

        override fun newArray(size: Int): Array<Clouds?> {
            return arrayOfNulls(size)
        }
    }

}