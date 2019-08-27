package com.empoderar.picar.presentation.data

import android.os.Parcel
import com.empoderar.picar.presentation.plataform.KParcelable
import com.empoderar.picar.presentation.plataform.parcelableCreator

data class BodyFormView(var id: Int,
                        var formId: Int,
                        var code: String,
                        var description: String,
                        var value: String,
                        var satisfy: String,
                        var date: String,
                        var comment: String): KParcelable {
    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::BodyFormView)
    }

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt(),
            parcel.readString(), parcel.readString(),
            parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeInt(formId)
            writeString(code)
            writeString(description)
            writeString(value)
            writeString(satisfy)
            writeString(date)
            writeString(comment)

        }
    }
}