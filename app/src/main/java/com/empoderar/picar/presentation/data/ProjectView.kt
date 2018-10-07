package com.empoderar.picar.presentation.data

import android.os.Parcel
import com.empoderar.picar.presentation.plataform.KParcelable
import com.empoderar.picar.presentation.plataform.parcelableCreator

data class ProjectView(var id: Int,
                       var unit: Int,
                       var municipality: String,
                       var type: Int,
                       var code: String,
                       var name: String,
                       var lat: Double,
                       var lon: Double,
                       var mount: Double,
                       var counterpart: Double,
                       var notFinance: Double,
                       var other: Double,
                       var total: Double): KParcelable {
    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::ProjectView)
    }

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt(),
            parcel.readString(), parcel.readInt(),
            parcel.readString(), parcel.readString(), parcel.readDouble(),
            parcel.readDouble(), parcel.readDouble(), parcel.readDouble(),
            parcel.readDouble(), parcel.readDouble(), parcel.readDouble())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeInt(unit)
            writeString(municipality)
            writeInt(type)
            writeString(code)
            writeString(name)
            writeDouble(lat)
            writeDouble(lon)
            writeDouble(mount)
            writeDouble(counterpart)
            writeDouble(notFinance)
            writeDouble(other)
            writeDouble(total)
        }
    }
}