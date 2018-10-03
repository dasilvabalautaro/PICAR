package com.empoderar.picar.presentation.data

import android.os.Parcel
import com.empoderar.picar.presentation.plataform.KParcelable
import com.empoderar.picar.presentation.plataform.parcelableCreator

data class UserView(var id: Int, var unit: Int,
                    var role: Int, var name: String,
                    var phone: String, var address: String): KParcelable {
    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::UserView)
    }

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt(),
            parcel.readInt(), parcel.readString(),
            parcel.readString(), parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeInt(unit)
            writeInt(role)
            writeString(name)
            writeString(phone)
            writeString(address)
        }
    }

}