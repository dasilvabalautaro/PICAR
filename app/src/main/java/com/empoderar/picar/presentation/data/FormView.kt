package com.empoderar.picar.presentation.data

import android.os.Parcel
import com.empoderar.picar.presentation.plataform.*
import java.util.*

data class FormView(var id: Int,
                    var project: Int,
                    var frmId: String,
                    var frmNro: Int,
                    var title: String,
                    var dateEvaluation: String,
                    var state: Int,
                    var observation: String?,
                    var userMobile: Int,
                    var latitude: Double,
                    var longitude: Double,
                    var dateCreation: String,
                    var dateModification: String): KParcelable {

    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::FormView)
    }

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt(),
            parcel.readString(), parcel.readInt(),
            parcel.readString(), parcel.readString(), parcel.readInt(),
            parcel.readString(), parcel.readInt(), parcel.readDouble(),
            parcel.readDouble(), parcel.readString(), parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeInt(project)
            writeString(frmId)
            writeInt(frmNro)
            writeString(title)
            writeString(dateEvaluation)
            writeInt(state)
            writeString(observation)
            writeInt(userMobile)
            writeDouble(latitude)
            writeDouble(longitude)
            writeString(dateCreation)
            writeString(dateModification)

        }
    }
}