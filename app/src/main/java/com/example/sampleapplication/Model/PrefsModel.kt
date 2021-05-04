package com.example.sampleapplication.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class PrefsModel(
    val weather: List<Weather>,
    val main: Main,
    val id: Int,
    val name: String,
    val sys: Sys,
    val dt: Long
) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
        arrayListOf<Weather>().apply {
            parcel.readList(this, Weather::class.java.classLoader)
        },
        parcel.readValue(Main::class.java.classLoader) as  Main,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readValue(Sys::class.java.classLoader) as  Sys,
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeValue(weather)
        p0?.writeValue(main)
        p0?.writeValue(id)
        p0?.writeValue(name)
        p0?.writeValue(sys)
        p0?.writeValue(dt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PrefsModel> {
        override fun createFromParcel(parcel: Parcel): PrefsModel {
            return PrefsModel(parcel)
        }

        override fun newArray(size: Int): Array<PrefsModel?> {
            return arrayOfNulls(size)
        }
    }

}