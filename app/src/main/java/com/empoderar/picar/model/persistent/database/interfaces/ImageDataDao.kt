package com.empoderar.picar.model.persistent.database.interfaces

import android.arch.persistence.room.*
import com.empoderar.picar.model.persistent.database.data.ImageData

@Dao
interface ImageDataDao {
    @Query("SELECT * FROM imageData")
    fun getAll(): List<ImageData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(imageData: ImageData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImages(images: List<ImageData>)

    @Query("DELETE FROM imageData")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(imageData: ImageData)
}