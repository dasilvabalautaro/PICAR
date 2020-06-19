package com.empoderar.picar.model.persistent.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contentFormData")
data class ContentFormData(@PrimaryKey var id: Int,
                           @ColumnInfo(name = "frmId") var frmId: String,
                           @ColumnInfo(name = "varTipo") var varTipo: String,
                           @ColumnInfo(name = "varCodigo") var varCodigo: String,
                           @ColumnInfo(name = "description") var description: String,
                           @ColumnInfo(name = "valor") var valor: Boolean,
                           @ColumnInfo(name = "logico") var logico: Boolean,
                           @ColumnInfo(name = "fecha") var fecha: Boolean,
                           @ColumnInfo(name = "texto") var texto: Boolean) {
    constructor(): this(0, "", "", "", "",
            false, false, false, false)
}