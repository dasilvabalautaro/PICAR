package com.empoderar.picar.model.persistent.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "municipalityData")
data class MunicipalityData(@PrimaryKey var id: String,
                            @ColumnInfo(name = "unit") var unit: Int,
                            @ColumnInfo(name = "name") var name: String) {
    constructor():this("", 0, "")
}