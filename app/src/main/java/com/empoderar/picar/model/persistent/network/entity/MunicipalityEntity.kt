package com.empoderar.picar.model.persistent.network.entity

import com.empoderar.picar.domain.data.Municipality

class MunicipalityEntity(private var id: String,
                         private var unit: Int,
                         private var name: String) {

    fun toMunicipality() = Municipality(id, unit, name)
}