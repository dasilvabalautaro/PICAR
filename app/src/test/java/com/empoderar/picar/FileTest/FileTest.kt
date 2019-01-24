package com.empoderar.picar.FileTest

import com.empoderar.picar.AndroidTest
import com.empoderar.picar.model.persistent.drive.DriveRepository
import com.empoderar.picar.model.persistent.drive.PermissionUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.robolectric.annotation.Config

//@Config(manifest= Config.NONE)
class FileTest: AndroidTest() {
    /*private lateinit var permissionUtils: PermissionUtils
    private lateinit var driveRepository: DriveRepository

    @Before
    fun setup(){
        permissionUtils = PermissionUtils()
        driveRepository = DriveRepository(permissionUtils)
    }

    @Test
    fun createFile(){
        driveRepository.writeFileDirectly("log.txt", "hola log")
    }

    @Test
    fun fileExist(){
        val result = driveRepository.isFileExist("log.txt")
        Assert.assertEquals("The file not exist.", false, result)
    }*/
}