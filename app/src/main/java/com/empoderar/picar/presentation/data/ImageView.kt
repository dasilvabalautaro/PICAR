package com.empoderar.picar.presentation.data

import android.os.Parcel
import com.empoderar.picar.presentation.plataform.KParcelable
import com.empoderar.picar.presentation.plataform.parcelableCreator

data class ImageView(var id: Int,
                     var form: Int,
                     var base64: String,
                     var date: String): KParcelable {

    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::ImageView)
    }

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt(),
            parcel.readString(), parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeInt(form)
            writeString(base64)
            writeString(date)
        }
    }


}