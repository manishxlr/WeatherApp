package com.example.sampleapplication.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class CityModel(
    val id: Int,
    val name : String,
    val state : String,
    val country : String
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
        p0?.writeValue(name)
        p0?.writeValue(state)
        p0?.writeValue(country)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CityModel> {
        override fun createFromParcel(parcel: Parcel): CityModel {
            return CityModel(parcel)
        }

        override fun newArray(size: Int): Array<CityModel?> {
            return arrayOfNulls(size)
        }
    }

}