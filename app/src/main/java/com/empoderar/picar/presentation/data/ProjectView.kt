package com.empoderar.picar.presentation.data

import android.os.Parcel
import com.empoderar.picar.presentation.plataform.KParcelable
import com.empoderar.picar.presentation.plataform.parcelableCreator

data class ProjectView(var id: Int,
                       var unity: Int,
                       var codeProject: String,
                       var nameProject: String,
                       var latitude: Double,
                       var longitude: Double,
                       var state: String,
                       var datePresentation: String,
                       var dateInitAgreement: String,
                       var dateEndAgreement: String): KParcelable {
    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::ProjectView)
    }

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt(),
            parcel.readString(), parcel.readString(),
            parcel.readDouble(), parcel.readDouble(), parcel.readString(),
            parcel.readString(), parcel.readString(), parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeInt(unity)
            writeString(codeProject)
            writeString(nameProject)
            writeDouble(latitude)
            writeDouble(longitude)
            writeString(state)
            writeString(datePresentation)
            writeString(dateInitAgreement)
            writeString(dateEndAgreement)

        }
    }
}