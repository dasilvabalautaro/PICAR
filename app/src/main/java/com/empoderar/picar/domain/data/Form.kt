package com.empoderar.picar.domain.data

import com.empoderar.picar.presentation.extension.empty
import java.util.*

data class Form(var id: Int,
                var project: Int,
                var frmId: String,
                var frmNro: Int,
                var title: String,
                var dateEvaluation: String,
                var state: Int,
                var observation: String?,
                var userMobile: Int,
                var latitude: Double,
                var longitude: Double,
                var dateCreation: String,
                var dateModification: String) {

    companion object {
        fun empty() = Form(0, 0, String.empty(), 0, String.empty(),
                String.empty(), 0, null, 0,
                0.0, 0.0, String.empty(), String.empty())
    }
}