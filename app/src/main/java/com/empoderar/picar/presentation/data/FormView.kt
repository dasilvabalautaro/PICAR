package com.empoderar.picar.presentation.data

import android.os.Parcel
import com.empoderar.picar.presentation.plataform.*
import java.util.*

data class FormView(var id: String,
                    var unit: Int,
                    var user: Int,
                    var dateForm: Date?,
                    var lat: Double,
                    var lon: Double,
                    var variable1: Double,
                    var variable2: Int,
                    var variable3: Date?,
                    var variable4: Boolean,
                    var comment1: String,
                    var comment2: String,
                    var comment3: String,
                    var updateDate: Date?,
                    var updateUser: Int): KParcelable {

    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::FormView)
    }

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readInt(),
            parcel.readInt(), parcel.readDate(),
            parcel.readDouble(), parcel.readDouble(), parcel.readDouble(),
            parcel.readInt(), parcel.readDate(), parcel.readBoolean(),
            parcel.readString(), parcel.readString(), parcel.readString(),
            parcel.readDate(), parcel.readInt())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(id)
            writeInt(unit)
            writeInt(user)
            writeDate(dateForm)
            writeDouble(lat)
            writeDouble(lon)
            writeDouble(variable1)
            writeInt(variable2)
            writeDate(variable3)
            writeBoolean(variable4)
            writeString(comment1)
            writeString(comment2)
            writeString(comment3)
            writeDate(updateDate)
            writeInt(updateUser)
        }
    }
}