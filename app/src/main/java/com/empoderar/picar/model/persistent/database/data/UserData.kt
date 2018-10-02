package com.empoderar.picar.model.persistent.database.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "userData")
data class UserData(@PrimaryKey var id: Int = 0,
                    @ColumnInfo(name = "unit") var unit: Int = 0,
                    @ColumnInfo(name = "role") var role: Int = 0,
                    @ColumnInfo(name = "name") var name: String = "",
                    @ColumnInfo(name = "phone") var phone: String = "",
                    @ColumnInfo(name = "address") var address: String = "",
                    @ColumnInfo(name = "password") var password: String = "")