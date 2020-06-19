package com.empoderar.picar.model.persistent.database.interfaces

import androidx.room.*
import com.empoderar.picar.model.persistent.database.data.RoleData

@Dao
interface RoleDataDao {

    @Query("SELECT * FROM roleData")
    fun getAll(): List<RoleData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roleData: RoleData)

    @Query("DELETE FROM roleData")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(roleData: RoleData)

}