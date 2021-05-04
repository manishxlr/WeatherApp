package com.example.sampleapplication.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class WeatherModel(
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val id: Int,
    val name: String,
    val sys: Sys,
    val dt : Long
) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readValue(Coord::class.java.classLoader) as  Coord,
        arrayListOf<Weather>().apply {
            parcel.readList(this, Weather::class.java.classLoader)
        },
        parcel.readValue(Main::class.java.classLoader) as  Main,
        parcel.readValue(Wind::class.java.classLoader) as  Wind,
        parcel.readValue(Clouds::class.java.classLoader) as  Clouds,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readValue(Sys::class.java.classLoader) as  Sys,
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeValue(coord)
        p0?.writeValue(weather)
        p0?.writeValue(main)
        p0?.writeValue(wind)
        p0?.writeValue(clouds)
        p0?.writeValue(id)
        p0?.writeValue(name)
        p0?.writeValue(sys)
        p0?.writeValue(dt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherModel> {
        override fun createFromParcel(parcel: Parcel): WeatherModel {
            return WeatherModel(parcel)
        }

        override fun newArray(size: Int): Array<WeatherModel?> {
            return arrayOfNulls(size)
        }
    }

}