package com.empoderar.picar.model.persistent.database.interfaces

import androidx.room.*
import com.empoderar.picar.model.persistent.database.data.FormData

@Dao
interface FormDataDao {
    @Query("SELECT * FROM formData WHERE upload = :upload")
    fun getAll(upload: Int): List<FormData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(formData: FormData): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForms(forms: List<FormData>)

    @Query("DELETE FROM formData")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(formData: FormData)

    @Query("UPDATE formData SET upload = :value WHERE id = :id")
    fun updateUpload(id: Int, value: Int)

    @Query("UPDATE formData SET upload = 2 WHERE upload = 1")
    fun updateAllUpload()

}