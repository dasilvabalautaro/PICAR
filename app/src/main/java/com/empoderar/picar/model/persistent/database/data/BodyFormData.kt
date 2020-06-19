package com.empoderar.picar.model.persistent.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bodyFormData")
data class BodyFormData(@PrimaryKey(autoGenerate = true) var id: Int,
                        @ColumnInfo(name = "formId") var formId: Int,
                        @ColumnInfo(name = "idProject") var idProject: Int,
                        @ColumnInfo(name = "code") var code: String,
                        @ColumnInfo(name = "value") var value: String,
                        @ColumnInfo(name = "description") var description: String,
                        @ColumnInfo(name = "satisfy") var satisfy: String,
                        @ColumnInfo(name = "date") var date: String,
                        @ColumnInfo(name = "comment") var comment: String) {
    constructor(): this(0, 0, 0, "",
            "", "", "", "", "")
}