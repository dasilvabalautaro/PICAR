package com.empoderar.picar.presentation.data

import android.os.Parcel
import com.empoderar.picar.presentation.plataform.KParcelable
import com.empoderar.picar.presentation.plataform.parcelableCreator

data class UnityView(var id: Int,
                     var name: String?,
                     var phone: String?,
                     var address: String?): KParcelable {
    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::UnityView)
    }

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString(),
            parcel.readString(), parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeString(name)
            writeString(phone)
            writeString(address)
        }
    }
}