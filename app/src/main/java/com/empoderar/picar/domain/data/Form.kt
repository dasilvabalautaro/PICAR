package com.empoderar.picar.domain.data

import com.empoderar.picar.presentation.extension.empty
import java.util.*

data class Form(var id: String,
                var unit: Int,
                var user: Int,
                var dateForm: Date?,
                var lat: Double,
                var lon: Double,
                var variable1: Double,
                var variable2: Int,
                var variable3: Date?,
                var variable4: Boolean,
                var comment1: String,
                var comment2: String,
                var comment3: String,
                var updateDate: Date?,
                var updateUser: Int) {

    companion object {
        fun empty() = Form(String.empty(), 0, 0, null, 0.0,
                0.0, 0.00, 0, null,
                false, String.empty(), String.empty(), String.empty(),
                null, 0)
    }
}