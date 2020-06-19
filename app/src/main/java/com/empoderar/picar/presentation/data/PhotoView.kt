package com.empoderar.picar.presentation.data

import android.graphics.Bitmap
import android.os.Parcel
import com.empoderar.picar.presentation.plataform.KParcelable
import com.empoderar.picar.presentation.plataform.parcelableCreator

data class PhotoView(var image: Bitmap,
                     var date: String?,
                     var latitude: Double,
                     var longitude: Double): KParcelable {
    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::PhotoView)
    }

    constructor(parcel: Parcel) : this(parcel.readParcelable<Bitmap>(Bitmap::class.java.classLoader) as Bitmap,
            parcel.readString(), parcel.readDouble(), parcel.readDouble())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeValue(image)
            writeString(date)
            writeDouble(latitude)
            writeDouble(longitude)

        }
    }

}