package com.empoderar.picar.presentation.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import com.empoderar.picar.R
import com.empoderar.picar.model.persistent.files.ManageFiles
import javax.inject.Inject
import javax.inject.Singleton
import org.jetbrains.anko.toast
import java.io.File
import java.lang.Exception

@Singleton
class EnablePermissions @Inject constructor(private val levelPermission:
                                            LevelPermission,
                                            private val manageFiles: ManageFiles,
                                            private val context: Context):
        ActivityCompat.OnRequestPermissionsResultCallback {

    val accessCamera = 1
    private val accessFineLocation = 2
    val accessReadExternal = 3
    private val accessWriteExternal = 4
    private val accessReadCalendar = 5
    private val accessWriteCalendar = 6
    private val accessReadPhoneState = 7


    fun startCamera(activity: Activity) {
        if (levelPermission.requestPermission(
                        activity,
                        accessCamera,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            try {
                val photoUri = FileProvider.getUriForFile(context,
                        context.applicationContext.packageName
                                + ".provider", manageFiles.getCameraFile())
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                activity.startActivityForResult(intent, accessCamera)

            }catch (ex: Exception){
                println("Error get image: " + ex.message)
            }


        }
    }


    fun startGalleryChooser(activity: Activity) {
        if (levelPermission.requestPermission(activity,
                        accessReadExternal,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activity.startActivityForResult(Intent.createChooser(intent,
                    activity.getString(R.string.select_image)),
                    accessReadExternal)
        }
    }


    fun permissionWriteExternal(activity: Activity){

        when {
            ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED -> if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                activity.toast(activity.getString(R.string.not_permission_write_external))

            } else {

                levelPermission
                        .requestPermission(activity, accessWriteExternal,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)

            }
        }
    }

    fun permissionReadExternal(activity: Activity){

        when {
            ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED -> if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                activity.toast(activity.getString(R.string.not_permission_read_external))

            } else {

                levelPermission
                        .requestPermission(activity, accessReadExternal,
                                Manifest.permission.READ_EXTERNAL_STORAGE)

            }
        }
    }


    fun permissionReadPhone(activity: Activity){

        when {
            ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.READ_PHONE_STATE) !=
                    PackageManager.PERMISSION_GRANTED -> if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.READ_PHONE_STATE)) {
                activity.toast(activity.getString(R.string.not_permission_read_phone))

            } else {

                levelPermission
                        .requestPermission(activity, accessReadPhoneState,
                                Manifest.permission.READ_PHONE_STATE)

            }
        }
    }

    fun permissionCamera(activity: Activity){

        when {
            ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED -> if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.CAMERA)) {
                activity.toast(activity.getString(R.string.not_permission_camera))

            } else {

                levelPermission
                        .requestPermission(activity, accessCamera,
                                Manifest.permission.CAMERA)

            }
        }
    }

    fun permissionServiceLocation(activity: Activity){

        when {
            ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED -> if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                activity.toast(activity.getString(R.string.not_permission_location))

            } else {

                levelPermission
                        .requestPermission(activity, accessFineLocation,
                                Manifest.permission.ACCESS_FINE_LOCATION)

            }
        }
    }

    fun permissionReadCalendar(activity: Activity){

        when {
            ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.READ_CALENDAR) !=
                    PackageManager.PERMISSION_GRANTED -> if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.READ_CALENDAR)) {
                activity.toast(activity.getString(R.string.not_permission_read_calendar))

            } else {

                levelPermission
                        .requestPermission(activity, accessReadCalendar,
                                Manifest.permission.READ_CALENDAR)

            }
        }
    }

    fun permissionWriteCalendar(activity: Activity){

        when {
            ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.WRITE_CALENDAR) !=
                    PackageManager.PERMISSION_GRANTED -> if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.WRITE_CALENDAR)) {
                activity.toast(activity.getString(R.string.not_permission_write_calendar))

            } else {

                levelPermission
                        .requestPermission(activity, accessWriteCalendar,
                                Manifest.permission.WRITE_CALENDAR)

            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        when(requestCode) {

            accessCamera -> if (levelPermission
                            .permissionGranted(requestCode,
                                    accessCamera,
                                    grantResults)) {
                println("PermissionEntity Ok")
            }
            accessFineLocation -> if (levelPermission
                            .permissionGranted(requestCode,
                                    accessFineLocation,
                                    grantResults)) {
                println("PermissionEntity Ok")
            }
            accessReadExternal -> if (levelPermission
                            .permissionGranted(requestCode,
                                    accessReadExternal,
                                    grantResults)) {
                println("PermissionEntity Ok")
            }
            accessWriteExternal -> if (levelPermission
                            .permissionGranted(requestCode,
                                    accessWriteExternal,
                                    grantResults)) {
                println("PermissionEntity Ok")
            }
            accessReadCalendar -> if (levelPermission
                            .permissionGranted(requestCode,
                                    accessReadCalendar,
                                    grantResults)) {
                println("PermissionEntity Ok")
            }

            accessWriteCalendar -> if (levelPermission
                            .permissionGranted(requestCode,
                                    accessWriteCalendar,
                                    grantResults)) {
                println("PermissionEntity Ok")
            }


            accessReadPhoneState -> if (levelPermission
                            .permissionGranted(requestCode,
                                    accessReadPhoneState,
                                    grantResults)) {
                println("PermissionEntity Ok")
            }

        }

    }


}