package com.empoderar.picar.domain.data

import com.empoderar.picar.presentation.extension.empty

data class Project(var id: Int,
                   var unit: Int,
                   var municipality: String,
                   var type: Int,
                   var code: String,
                   var name: String,
                   var lat: Double,
                   var lon: Double,
                   var mount: Double,
                   var counterpart: Double,
                   var notFinance: Double,
                   var other: Double,
                   var total: Double) {
    companion object {
        fun empty() = Project(0, 0, String.empty(), 0,
                String.empty(), String.empty(), 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0)
    }
}