package com.empoderar.picar.model.persistent.network.entity

import com.empoderar.picar.domain.data.Permission

data class PermissionEntity(private var token: String,
                            private var expiration: String,
                            private var email: String,
                            private var id: Int,
                            private var unity: Int) {
    fun toPermission() = Permission(token,
            expiration, email, id, unity)
}