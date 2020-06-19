package com.empoderar.picar.presentation.data

import android.os.Parcel
import com.empoderar.picar.presentation.plataform.*

data class PointView(var latitude: Double,
                     var longitude: Double,
                     var title: String?): KParcelable  {

    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::PointView)
    }

    constructor(parcel: Parcel) : this(parcel.readDouble(), parcel.readDouble(),
            parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeDouble(latitude)
            writeDouble(longitude)
            writeString(title)
        }
    }
}