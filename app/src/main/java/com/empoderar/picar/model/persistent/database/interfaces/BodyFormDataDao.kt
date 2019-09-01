package com.empoderar.picar.model.persistent.database.interfaces

import android.arch.persistence.room.*
import com.empoderar.picar.model.persistent.database.data.BodyFormData

@Dao
interface BodyFormDataDao {
    @Query("SELECT * FROM bodyFormData")
    fun getAll(): List<BodyFormData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bodyFormData: BodyFormData): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBodyForms(bodyForms: List<BodyFormData>)

    @Query("DELETE FROM bodyFormData")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(bodyFormData: BodyFormData)

    @Query("SELECT * FROM bodyFormData WHERE formId LIKE :formId")
    fun getBodyByIdForm(formId: Int): List<BodyFormData>

}