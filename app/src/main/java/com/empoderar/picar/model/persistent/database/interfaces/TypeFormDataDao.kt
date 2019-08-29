package com.empoderar.picar.model.persistent.database.interfaces

import android.arch.persistence.room.*
import com.empoderar.picar.model.persistent.database.data.TypeFormData

@Dao
interface TypeFormDataDao {
    @Query("SELECT * FROM typeFormData")
    fun getAll(): List<TypeFormData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(typeFormData: TypeFormData): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypesForm(typesForm: List<TypeFormData>)

    @Query("DELETE FROM typeFormData")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(typeFormData: TypeFormData)
}
