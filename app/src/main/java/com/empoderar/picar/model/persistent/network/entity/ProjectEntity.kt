package com.empoderar.picar.model.persistent.network.entity

import com.empoderar.picar.domain.data.Project

data class ProjectEntity(private var id: Int,
                         private var unit: Int,
                         private var municipality: String,
                         private var type: Int,
                         private var code: String,
                         private var name: String,
                         private var lat: Double,
                         private var lon: Double,
                         private var mount: Double,
                         private var counterpart: Double,
                         private var notFinance: Double,
                         private var other: Double,
                         private var total: Double) {
    fun toProject() = Project(id, unit, municipality, type, code,
            name, lat, lon, mount, counterpart, notFinance, other, total)

}