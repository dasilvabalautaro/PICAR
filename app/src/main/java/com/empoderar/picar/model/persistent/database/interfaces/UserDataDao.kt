package com.empoderar.picar.model.persistent.database.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.empoderar.picar.model.persistent.database.data.UserData

@Dao
interface UserDataDao {

    @Query("SELECT * FROM userData")
    fun getAll(): List<UserData>

    @Insert(onConflict = REPLACE)
    fun insert(userData: UserData)

    @Insert(onConflict = REPLACE)
    fun insertUsers(users: List<UserData>)

    @Query("DELETE FROM userData")
    fun deleteAll()

    @Update(onConflict = REPLACE)
    fun update(userData: UserData)

    @Query("SELECT * FROM userData WHERE id = :id")
    fun findUserById(id: Int): UserData

    @Query("SELECT * FROM userData WHERE name LIKE :name")
    fun findUserByName(name: String): List<UserData>

}