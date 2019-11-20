package com.empoderar.picar.presentation.tools

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


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

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
        return format.format(date)
    }

    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
        return df.parse(date).time
    }

    @SuppressLint("NewApi")
    fun convertToDateViaInstant(dateToConvert: LocalDate): Date? {
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant())
    }
}