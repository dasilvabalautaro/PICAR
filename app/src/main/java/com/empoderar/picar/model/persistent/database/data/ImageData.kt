package com.empoderar.picar.model.persistent.database.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "imageData")
data class ImageData(@PrimaryKey(autoGenerate = true) var id: Int,
                     @ColumnInfo(name = "form") var form: Int,
                     @ColumnInfo(name = "project") var project: Int,
                     @ColumnInfo(name = "base64") var base64: String,
                     @ColumnInfo(name = "latitude") var latitude: Double,
                     @ColumnInfo(name = "longitude") var longitude: Double,
                     @ColumnInfo(name = "date") var date: String) {
    constructor(): this(0,0,0, "",
            0.0, 0.0, "" )
}