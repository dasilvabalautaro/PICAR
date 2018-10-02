package com.empoderar.picar.model.persistent.database.interfaces

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.empoderar.picar.model.persistent.database.data.UserData

@Dao
interface UserDataDao {

    @Query("SELECT * FROM userData")
    fun getAll(): List<UserData>

    @Insert(onConflict = REPLACE)
    fun insert(userData: UserData)

    @Query("DELETE FROM userData")
    fun deleteAll()

    @Update(onConflict = REPLACE)
    fun update(userData: UserData)

    @Query("SELECT * FROM userData WHERE id = :id")
    fun findUserById(id: Int): UserData

    @Query("SELECT * FROM userData WHERE name LIKE :name")
    fun findUserByName(name: String): List<UserData>

}