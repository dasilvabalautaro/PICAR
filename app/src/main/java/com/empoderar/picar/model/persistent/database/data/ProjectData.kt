package com.empoderar.picar.model.persistent.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projectData")
data class ProjectData(@PrimaryKey var id: Int,
                       @ColumnInfo(name = "unity") var unity: Int,
                       @ColumnInfo(name = "codeProject") var codeProject: String,
                       @ColumnInfo(name = "nameProject") var nameProject: String,
                       @ColumnInfo(name = "latitude") var latitude: Double,
                       @ColumnInfo(name = "longitude") var longitude: Double,
                       @ColumnInfo(name = "state") var state: String,
                       @ColumnInfo(name = "datePresentation") var datePresentation: String,
                       @ColumnInfo(name = "dateInitAgreement") var dateInitAgreement: String,
                       @ColumnInfo(name = "dateEndAgreement") var dateEndAgreement: String) {
    constructor():this(0, 0, "", "", 0.0,
            0.0, "", "", "",
            "")
}
