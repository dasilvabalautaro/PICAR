package com.empoderar.picar.model.persistent.database.interfaces

import android.arch.persistence.room.*
import com.empoderar.picar.model.persistent.database.data.FormData

@Dao
interface FormDataDao {
    @Query("SELECT * FROM formData")
    fun getAll(): List<FormData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(formData: FormData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForms(forms: List<FormData>)

    @Query("DELETE FROM formData")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(formData: FormData)
}