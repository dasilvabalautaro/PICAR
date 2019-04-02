package com.empoderar.picar.presentation.tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

object Transform {
    private const val quality = 100

    fun base641EncodedImage(bitmap: Bitmap): String{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
        val imageBytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    fun base64DecodeImage(baseString: String): Bitmap {
        val decode: ByteArray = Base64.decode(baseString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decode,
                0, decode.size)
    }

}