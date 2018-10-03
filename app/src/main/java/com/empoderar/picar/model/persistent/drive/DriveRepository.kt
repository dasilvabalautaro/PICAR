package com.empoderar.picar.model.persistent.drive

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import com.empoderar.picar.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DriveRepository @Inject constructor(private val permissionUtils:
                                          PermissionUtils):
        ActivityCompat.OnRequestPermissionsResultCallback{

    private val galleryPermissionsRequest = 0
    private val writeExternalStorage = 1
    private val readExternalStorage = 2
    private val cameraPermissionsRequest = 3
    private val galleryImageRequest = 4
    private val cameraImageRequest = 5
    private val directoryWork = "picar"
    private val imageFile = "temp.jpg"

    private fun getWorkStorageDir(): File {
        val file = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), directoryWork)
        if (!file.mkdirs()) {
            println("Directory not created")
        }
        return file
    }

    fun startGalleryChooser(activity: Activity) {
        if (permissionUtils.requestPermission(activity,
                        galleryPermissionsRequest,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activity.startActivityForResult(Intent.createChooser(intent,
                    activity.getString(R.string.select_photo)),
                    galleryImageRequest)
        }
    }

    fun startCamera(activity: Activity) {
        if (permissionUtils.requestPermission(
                        activity,
                        cameraPermissionsRequest,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoUri = FileProvider.getUriForFile(activity,
                    activity.applicationContext.packageName
                            + ".provider", getCameraFile(activity))
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            activity.startActivityForResult(intent, cameraImageRequest)

        }
    }

    fun getImageFile(activity: Activity, file: String): Bitmap?{
        when {
            ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED -> permissionUtils
                    .requestPermission(activity,
                            readExternalStorage,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
            else -> return getBitmap(file, activity)
        }
        return null
    }

    private fun getBitmap(file: String, activity: Activity): Bitmap?{
        var bitmap: Bitmap? = null
        try {
            val pathImage = File(getWorkStorageDir(), file)
            val contentUri = Uri.fromFile(pathImage)
            bitmap = BitmapFactory.decodeStream(activity
                    .contentResolver.openInputStream(contentUri))

        }catch (ie: IOException){
            if (ie.message != null){
                println(ie.message)
            }
        }
        return bitmap
    }

    @Throws(IOException::class)
    private fun saveBitmapToJPG(bitmap: Bitmap, file: String) {
        val pathImage = File(getWorkStorageDir(), file)
        val newBitmap = scaleBitmap(bitmap)
        val stream = FileOutputStream(pathImage)
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.close()
    }

    fun saveImageFile(activity: Activity, file: String, bitmap: Bitmap){
        try {
            when {
                ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED -> permissionUtils
                        .requestPermission(activity,
                                writeExternalStorage,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                else -> saveBitmapToJPG(bitmap, file)
            }

        }catch (ex: IOException){
            println(ex.message)
        }

    }

    private fun getCameraFile(activity: Activity): File {
        val dir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(dir, imageFile)
    }

    fun permissionCamera(activity: Activity): Boolean{
        return permissionUtils.requestPermission(
                activity,
                cameraPermissionsRequest,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
    }

    private fun scaleBitmap(bitmap: Bitmap): Bitmap{
        val newWidth = bitmap.width/2
        val newHeight = bitmap.height/(bitmap.width/newWidth)
        val newBitmap = Bitmap.createBitmap(newWidth,
                newHeight, Bitmap.Config.ARGB_8888)

        val ratioX = newWidth / bitmap.width.toFloat()
        val ratioY = newHeight / bitmap.height.toFloat()
        val middleX = newWidth / 2.0f
        val middleY = newHeight / 2.0f

        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

        val c = Canvas(newBitmap)
        c.matrix = scaleMatrix
        c.drawBitmap(bitmap, middleX - bitmap.width / 2,
                middleY - bitmap.height / 2, Paint(Paint.FILTER_BITMAP_FLAG))
        return newBitmap
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        when(requestCode) {

            writeExternalStorage ->{
                if (permissionUtils
                                .permissionGranted(requestCode,
                                        writeExternalStorage,
                                        grantResults)) {
                    println("Permission write")
                }
            }
            readExternalStorage ->{
                if (permissionUtils
                                .permissionGranted(requestCode,
                                        readExternalStorage,
                                        grantResults)) {
                    println("Permission read")
                }
            }
            cameraPermissionsRequest -> if (permissionUtils
                            .permissionGranted(requestCode,
                                    cameraPermissionsRequest,
                                    grantResults)) {

                println("Permission Ok")
            }
            galleryPermissionsRequest -> if (permissionUtils
                            .permissionGranted(requestCode,
                                    galleryPermissionsRequest,
                                    grantResults)) {
                println("Permission Ok")
            }


        }

    }

    fun isFileExist(file: String): Boolean{
        val fileFind = File(getWorkStorageDir(), file)
        if (fileFind.isFile){
            return true
        }
        return false
    }

    fun readFileDirectlyAsText(file: String): String = File(getWorkStorageDir(), file)
            .readText(Charsets.UTF_8)

    fun writeFileDirectly(file: String, fileContent: String) =
            File(getWorkStorageDir(), file).writeText(fileContent + "\n")

    private fun deleteImagesFile(){

        val file = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), directoryWork)
        if (file.isDirectory) {
            val files = file.listFiles()
            files?.asSequence()?.filter {
                (it.endsWith(".jpg") || it.endsWith(".jpeg"))
                        && it.delete() }?.forEach { _ -> println("Delete OK") }
        }
    }

    fun deleteImage(activity: Activity){

        when {
            ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED -> permissionUtils
                    .requestPermission(activity,
                            readExternalStorage,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
            else -> deleteImagesFile()
        }

    }
}