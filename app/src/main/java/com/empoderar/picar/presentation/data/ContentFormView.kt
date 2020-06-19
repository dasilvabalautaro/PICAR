package com.empoderar.picar.presentation.data

import android.annotation.SuppressLint
import android.os.Build
import android.os.Parcel
import androidx.annotation.RequiresApi
import com.empoderar.picar.presentation.plataform.KParcelable
import com.empoderar.picar.presentation.plataform.parcelableCreator

data class ContentFormView(var id: Int,
                           var frmId: String?,
                           var varTipo: String?,
                           var varCodigo: String?,
                           var description: String?,
                           var valor: Boolean,
                           var logico: Boolean,
                           var fecha: Boolean,
                           var texto: Boolean): KParcelable {
    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::ContentFormView)
    }


    @SuppressLint("NewApi")
    constructor(parcel: Parcel) : this(parcel.readInt(),
            parcel.readString(), parcel.readString(), parcel.readString(),
            parcel.readString(), parcel.readBoolean(), parcel.readBoolean(),
            parcel.readBoolean(), parcel.readBoolean())

    @SuppressLint("NewApi")
    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeString(frmId)
            writeString(varTipo)
            writeString(varCodigo)
            writeString(description)
            writeBoolean(valor)
            writeBoolean(logico)
            writeBoolean(fecha)
            writeBoolean(texto)

        }
    }

}