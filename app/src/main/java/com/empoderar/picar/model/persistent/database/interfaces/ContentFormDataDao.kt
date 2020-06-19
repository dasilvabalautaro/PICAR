package com.empoderar.picar.model.persistent.database.interfaces

import androidx.room.*
import com.empoderar.picar.model.persistent.database.data.ContentFormData
import com.empoderar.picar.model.persistent.database.data.UserData

@Dao
interface ContentFormDataDao {
    @Query("SELECT * FROM contentFormData")
    fun getAll(): List<ContentFormData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contentFormData: ContentFormData): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContentForms(contentForms: List<ContentFormData>)

    @Query("DELETE FROM contentFormData")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(contentFormData: ContentFormData)

    @Query("SELECT * FROM contentFormData WHERE frmId LIKE :frmId")
    fun getContentByTypeForm(frmId: String): List<ContentFormData>
}