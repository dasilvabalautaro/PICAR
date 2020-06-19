package com.empoderar.picar.model.persistent.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unityData")
data class UnityData(@PrimaryKey var id: Int,
                     @ColumnInfo(name = "name") var name: String,
                     @ColumnInfo(name = "short") var short: String) {
    constructor():this(0, "", "")
}