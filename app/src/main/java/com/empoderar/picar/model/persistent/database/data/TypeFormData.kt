package com.empoderar.picar.model.persistent.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "typeFormData")
data class TypeFormData(@PrimaryKey var frmID: String,
                        @ColumnInfo(name = "idStep") var idStep: String,
                        @ColumnInfo(name = "title") var title: String,
                        @ColumnInfo(name = "max") var max: Double,
                        @ColumnInfo(name = "weigh") var weigh: Double,
                        @ColumnInfo(name = "observation") var observation: String,
                        @ColumnInfo(name = "maxBit") var maxBit: Boolean,
                        @ColumnInfo(name = "valueBit") var valueBit: Boolean,
                        @ColumnInfo(name = "logicBit") var logicBit: Boolean,
                        @ColumnInfo(name = "dateBit") var dateBit: Boolean,
                        @ColumnInfo(name = "textBit") var textBit: Boolean) {
    constructor(): this("", "", "", 0.0, 0.0,
            "", false, false, false,
            false, false)
}