package com.empoderar.picar.domain.data

import com.empoderar.picar.presentation.extension.empty

data class Project(var id: Int,
                   var unity: Int,
                   var codeProject: String,
                   var nameProject: String,
                   var latitude: Double,
                   var longitude: Double,
                   var state: String,
                   var datePresentation: String,
                   var dateInitAgreement: String,
                   var dateEndAgreement: String) {
    companion object {
        fun empty() = Project(0, 0, String.empty(), String.empty(),
                0.0, 0.0, String.empty(), String.empty(), String.empty(),
                String.empty())
    }
}