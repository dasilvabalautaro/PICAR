package com.empoderar.picar.model.persistent.database.interfaces

import android.arch.persistence.room.*
import com.empoderar.picar.model.persistent.database.data.UnityData

@Dao
interface UnityDataDao {

    @Query("SELECT * FROM unityData")
    fun getAll(): List<UnityData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(unityData: UnityData)

    @Query("DELETE FROM unityData")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(unityData: UnityData)
}