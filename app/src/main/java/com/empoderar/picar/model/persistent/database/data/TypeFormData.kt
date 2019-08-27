package com.empoderar.picar.model.persistent.database.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "typeFormData")
data class TypeFormData(@PrimaryKey var frmID: String,
                        @ColumnInfo(name = "idStep") var idStep: String,
                        @ColumnInfo(name = "title") var title: String,
                        @ColumnInfo(name = "max") var max: Float,
                        @ColumnInfo(name = "weigh") var weigh: Float,
                        @ColumnInfo(name = "observation") var observation: String,
                        @ColumnInfo(name = "maxBit") var maxBit: Boolean,
                        @ColumnInfo(name = "valueBit") var valueBit: Boolean,
                        @ColumnInfo(name = "logicBit") var logicBit: Boolean,
                        @ColumnInfo(name = "dateBit") var dateBit: Boolean,
                        @ColumnInfo(name = "textBit") var textBit: Boolean) {
    constructor(): this("", "", "", 0.0f, 0.0f,
            "", false, false, false,
            false, false)
}