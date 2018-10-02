package com.empoderar.picar.model.persistent.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.empoderar.picar.model.persistent.database.data.*
import com.empoderar.picar.model.persistent.database.interfaces.*

@Database(entities = [UserData::class, UnityData::class,
    ProjectData::class, MunicipalityData::class, RoleData::class],
        version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDataDao
    abstract fun unityDao(): UnityDataDao
    abstract fun projectDao(): ProjectDataDao
    abstract fun municipalityDao(): MunicipalityDataDao
    abstract fun roleDao(): RoleDataDao
}