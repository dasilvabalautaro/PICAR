package com.empoderar.picar.model.persistent.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "formData")
data class FormData(@PrimaryKey(autoGenerate = true) var id: Int,
                    @ColumnInfo(name = "project") var project: Int,
                    @ColumnInfo(name = "frmId") var frmId: String,
                    @ColumnInfo(name = "frmNro") var frmNro: Int,
                    @ColumnInfo(name = "title") var title: String,
                    @ColumnInfo(name = "dateEvaluation") var dateEvaluation: String,
                    @ColumnInfo(name = "state") var state: Int,
                    @ColumnInfo(name = "observation") var observation: String?,
                    @ColumnInfo(name = "userMobile") var userMobile: Int,
                    @ColumnInfo(name = "latitudeMobile") var latitude: Double,
                    @ColumnInfo(name = "longitudeMobile") var longitude: Double,
                    @ColumnInfo(name = "dateCreation") var dateCreation: String,
                    @ColumnInfo(name = "dateModification") var dateModification: String,
                    @ColumnInfo(name = "upload") var upload: Int) {

    constructor():this(0, 0, "", 0, "",
            "", 0, "", 0,
            0.0, 0.0, "", "", 0)
}