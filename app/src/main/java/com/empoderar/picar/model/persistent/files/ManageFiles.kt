package com.empoderar.picar.model.persistent.files

import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.DisplayMetrics
import com.empoderar.picar.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.windowManager
import java.io.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ManageFiles @Inject constructor(private val context: Context) {
    private val directoryWork = "picar"
    private val displayMetrics = DisplayMetrics()
    private var screenWidth = 0
    private var screenHeight = 0
    private val imageFile = "temp.jpg"
    private val quality = 100

    init {
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        this.screenWidth = displayMetrics.widthPixels
        this.screenHeight = displayMetrics.heightPixels
    }

    private fun getStorageDirectory(): File {
        val file = File(Environment.getExternalStorageDirectory(), directoryWork)
        if (!file.exists()) {
            file.mkdirs()
            println("Directory not created")
        }
        return file
    }

    fun writeFile(fileName: String, data: String){
        GlobalScope.launch {
            try {
                val file = File(getStorageDirectory(), fileName)
                if (!file.exists()){
                    file.createNewFile()
                }
                val out = BufferedWriter(FileWriter(file, true) as Writer, 1024)
                out.write(data)
                out.newLine()
                out.close()

            }catch (io: IOException){
                println(io.message)
            }
        }
    }

    fun readFromFile(fileName: String): String? {

        try {
            val file = File(getStorageDirectory(), fileName)
            val bufferedReader = BufferedReader(FileReader(file))
            val stringBuilder = StringBuilder()
            var lineTxt = bufferedReader.readLine()
            while (!lineTxt.isNullOrEmpty()) {
                stringBuilder.appendln(lineTxt)
                lineTxt = bufferedReader.readLine()
            }

            return stringBuilder.toString()

        } catch (e: FileNotFoundException) {
            println("File not found: $e")
        } catch (e: IOException) {
            println("Can not read file: $e")
        }
        return null

    }

    fun getBitmap(uri: Uri?): Bitmap? {
        when {
            uri != null -> return try {
                scaleBitmapDown(
                        MediaStore.Images.Media
                                .getBitmap(context.contentResolver, uri))

            } catch (e: IOException) {
                println(e.message)
                null
            }
            else -> {
                println(R.string.value_null)
                return null
            }

        }
    }

    @Throws(IOException::class)
    fun saveBitmapToJPG(bitmap: Bitmap, file: String) {
        val pathImage = File(getStorageDirectory(), file)
        val stream = FileOutputStream(pathImage)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.close()
    }

    private fun scaleBitmapDown(bitmap: Bitmap): Bitmap {
        return if (this.screenWidth < bitmap.width){
            val diffRelationSize = this.screenWidth.toFloat() / bitmap.width.toFloat()
            Bitmap.createScaledBitmap(bitmap,
                    (bitmap.width * diffRelationSize).toInt(),
                    (bitmap.height * diffRelationSize).toInt(), true)
        }else{
            bitmap
        }

/*
        if (this.screenWidth < bitmap.width){
            val diffRelationSize = this.screenWidth.toFloat() / bitmap.width.toFloat()
            val newWidth = (bitmap.width.toFloat() * diffRelationSize).toInt()
            val newHeight = (bitmap.height.toFloat() * diffRelationSize).toInt()
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
            c.drawBitmap(bitmap, middleX - bitmap.width/2,
                    middleY - bitmap.height/2,
                    Paint(Paint.FILTER_BITMAP_FLAG))
            return newBitmap

        }else{
            return bitmap
        }
*/
    }

    fun isFileExist(file: String): Boolean{
        val fileFind = File(getStorageDirectory(), file)
        if (fileFind.isFile){
            return true
        }
        return false
    }

    private fun deleteImagesJpeg(){

        val file = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), directoryWork)
        if (file.isDirectory) {
            val files = file.listFiles()
            files?.asSequence()?.filter {
                (it.endsWith(".jpg") || it.endsWith(".jpeg"))
                        && it.delete() }?.forEach { _ -> println("Delete OK") }
        }
    }

    fun deleteFile(fileName: String){
        try {
            val file = File(getStorageDirectory(), fileName)
            file.delete()
        } catch (e: FileNotFoundException) {
            println("File not found: $e")
        } catch (e: IOException) {
            println("Can not read file: $e")
        }

    }

    fun getCameraFile(): File {
        val dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!dir.exists()) {
            dir.mkdirs()
            println("Directory not created")
        }
        return File(dir, imageFile)
    }

    fun readFileDirectlyAsText(file: String): String = File(getStorageDirectory(), file)
            .readText(Charsets.UTF_8)

    fun writeFileDirectly(file: String, fileContent: String) =
            File(getStorageDirectory(), file).writeText(fileContent + "\n")

    fun base641EncodedImage(bitmap: Bitmap): String{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
        val imageBytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }
}