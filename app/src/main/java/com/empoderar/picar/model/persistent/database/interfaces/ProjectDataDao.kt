package com.empoderar.picar.model.persistent.database.interfaces

import androidx.room.*
import com.empoderar.picar.model.persistent.database.data.ProjectData

@Dao
interface ProjectDataDao {

    @Query("SELECT * FROM projectData")
    fun getAll(): List<ProjectData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectData: ProjectData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProjects(projects: List<ProjectData>)


    @Query("DELETE FROM projectData")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(projectData: ProjectData)

}