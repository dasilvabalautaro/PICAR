package com.empoderar.picar.domain.data

import com.empoderar.picar.presentation.extension.empty

data class Unity(var id: Int,
                 var name: String,
                 var phone: String,
                 var address: String) {

    companion object {
        fun empty() = Unity(0, String.empty(), String.empty(), String.empty())
    }
}