package com.empoderar.picar.model.persistent.database.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "projectData")
data class ProjectData(@PrimaryKey var id: Int,
                       @ColumnInfo(name = "unit") var unit: Int,
                       @ColumnInfo(name = "municipality") var municipality: String,
                       @ColumnInfo(name = "type") var type: Int,
                       @ColumnInfo(name = "code") var code: String,
                       @ColumnInfo(name = "name") var name: String,
                       @ColumnInfo(name = "latitude") var lat: Double,
                       @ColumnInfo(name = "longitude") var lon: Double,
                       @ColumnInfo(name = "mount") var mount: Double,
                       @ColumnInfo(name = "counterpart") var counterpart: Double,
                       @ColumnInfo(name = "notFinance") var notFinance: Double,
                       @ColumnInfo(name = "other") var other: Double,
                       @ColumnInfo(name = "total") var total: Double) {
    constructor():this(0, 0, "", 0, "",
            "", 0.00, 0.00, 0.00,
            0.00, 0.00, 0.00, 0.00)
}