package com.empoderar.picar.model.persistent.network.entity

import com.empoderar.picar.domain.data.User

data class UserEntity(private var id: Int, private var unit: Int,
                      private var role: Int, private var name: String,
                      private var phone: String, private var address: String) {

    fun toUser() = User(id, unit, role, name, phone, address)
}