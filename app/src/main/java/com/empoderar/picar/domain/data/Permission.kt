package com.empoderar.picar.domain.data

import com.empoderar.picar.presentation.extension.empty

data class Permission(var token: String,
                      var expiration: String,
                      var email: String,
                      var id: Int,
                      var unity: Int) {
    companion object{
        fun empty() = Permission(String.empty(),
                String.empty(), String.empty(), 0, 0)
    }
}