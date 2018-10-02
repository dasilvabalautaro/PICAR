package com.empoderar.picar.model.persistent.database.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "municipalityData")
data class MunicipalityData(@PrimaryKey var id: String,
                            @ColumnInfo(name = "unit") var unit: Int,
                            @ColumnInfo(name = "name") var name: String) {
    constructor():this("", 0, "")
}