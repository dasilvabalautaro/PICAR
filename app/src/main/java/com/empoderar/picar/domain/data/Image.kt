package com.empoderar.picar.domain.data

import com.empoderar.picar.presentation.extension.empty


data class Image(var id: Int,
                 var form: Int,
                 var project: Int,
                 var base64: String,
                 var latitude: Double,
                 var longitude: Double,
                 var date: String) {
    companion object{
        fun empty() = Image(0, 0,0, String.empty(),
                0.0, 0.0, String.empty())
    }
}