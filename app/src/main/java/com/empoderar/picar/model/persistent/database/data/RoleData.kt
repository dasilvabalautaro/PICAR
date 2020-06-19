package com.empoderar.picar.model.persistent.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roleData")
data class RoleData(@PrimaryKey var id: Int,
                    @ColumnInfo(name = "name") var name: String) {
    constructor():this(0, "")
}