package com.empoderar.picar.presentation.data

import android.annotation.SuppressLint
import android.os.Parcel
import com.empoderar.picar.presentation.plataform.KParcelable
import com.empoderar.picar.presentation.plataform.parcelableCreator

data class TypeFormView(var frmId: String?,
                        var idEtapa: String?,
                        var titulo: String?,
                        var maximo: Double,
                        var ponderacion: Double,
                        var observation: String?,
                        var maxi: Boolean,
                        var valor: Boolean,
                        var logico: Boolean,
                        var fecha: Boolean,
                        var texto: Boolean): KParcelable {
    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::TypeFormView)
    }

    @SuppressLint("NewApi")
    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString(),
            parcel.readString(), parcel.readDouble(),
            parcel.readDouble(), parcel.readString(), parcel.readBoolean(),
            parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(),
            parcel.readBoolean())

    @SuppressLint("NewApi")
    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(frmId)
            writeString(idEtapa)
            writeString(titulo)
            writeDouble(maximo)
            writeDouble(ponderacion)
            writeString(observation)
            writeBoolean(maxi)
            writeBoolean(valor)
            writeBoolean(logico)
            writeBoolean(fecha)
            writeBoolean(texto)

        }
    }

}