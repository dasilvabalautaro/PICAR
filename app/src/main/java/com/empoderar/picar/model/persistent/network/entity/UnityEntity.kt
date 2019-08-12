package com.empoderar.picar.model.persistent.network.entity

import com.empoderar.picar.domain.data.Unity

data class UnityEntity(private var id: Int,
                       private var name: String,
                       private var short: String) {

    fun toUnity() = Unity(id, name, short)
}