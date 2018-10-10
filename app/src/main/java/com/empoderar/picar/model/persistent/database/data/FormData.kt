package com.empoderar.picar.model.persistent.database.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "formData")
data class FormData(@PrimaryKey var id: String,
                    @ColumnInfo(name = "project") var unit: Int,
                    @ColumnInfo(name = "user") var user: Int,
                    @ColumnInfo(name = "dateForm") var dateForm: Long?,
                    @ColumnInfo(name = "latitude") var lat: Double,
                    @ColumnInfo(name = "longitude") var lon: Double,
                    @ColumnInfo(name = "variable1") var variable1: Double,
                    @ColumnInfo(name = "variable2") var variable2: Int,
                    @ColumnInfo(name = "variable3") var variable3: Long?,
                    @ColumnInfo(name = "variable4") var variable4: Boolean,
                    @ColumnInfo(name = "comment1") var comment1: String,
                    @ColumnInfo(name = "comment2") var comment2: String,
                    @ColumnInfo(name = "comment3") var comment3: String,
                    @ColumnInfo(name = "updateDate") var updateDate: Long?,
                    @ColumnInfo(name = "updateUser") var updateUser: Int) {

    constructor():this("", 0, 0, null, 0.0,
            0.0, 0.00, 0, null,
            false, "", "", "", null, 0)
}