package com.empoderar.picar.presentation.data

import com.empoderar.picar.presentation.extension.empty

data class User(var id: Int, var unit: Int,
                var role: Int, var name: String,
                var phone: String, var address: String) {
    companion object {
        fun empty() = User(0, 0, 0, String.empty(),
                String.empty(), String.empty())
    }

}