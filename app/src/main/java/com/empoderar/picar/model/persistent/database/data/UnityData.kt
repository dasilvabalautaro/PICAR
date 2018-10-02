package com.empoderar.picar.model.persistent.database.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "unityData")
data class UnityData(@PrimaryKey var id: Int,
                     @ColumnInfo(name = "name") var name: String,
                     @ColumnInfo(name = "phone") var phone: String,
                     @ColumnInfo(name= "address") var address: String) {
    constructor():this(0, "", "", "")
}