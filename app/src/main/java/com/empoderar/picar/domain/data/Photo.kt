package com.empoderar.picar.domain.data

import android.graphics.Bitmap
import com.empoderar.picar.presentation.extension.empty

data class Photo(var image: Bitmap?,
                 var date: String) {
    companion object{
        fun empty() = Photo(null, String.empty())
    }
}