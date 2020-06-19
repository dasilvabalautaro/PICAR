package com.empoderar.picar.model.persistent.database.interfaces

import androidx.room.*
import com.empoderar.picar.model.persistent.database.data.MunicipalityData

@Dao
interface MunicipalityDataDao {

    @Query("SELECT * FROM municipalityData")
    fun getAll(): List<MunicipalityData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(municipalityData: MunicipalityData)

    @Query("DELETE FROM municipalityData")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(municipalityData: MunicipalityData)
}