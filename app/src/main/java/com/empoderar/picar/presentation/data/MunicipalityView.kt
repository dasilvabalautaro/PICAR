package com.empoderar.picar.presentation.data

import android.os.Parcel
import com.empoderar.picar.presentation.plataform.*

data class MunicipalityView(var id: String,
                            var unit: Int,
                            var name: String): KParcelable {

    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::MunicipalityView)
    }

    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readInt(),
            parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(id)
            writeInt(unit)
            writeString(name)

        }
    }
}