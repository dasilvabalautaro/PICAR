package com.empoderar.picar.domain.data

import com.empoderar.picar.presentation.extension.empty

data class Municipality(var id: String,
                        var unit: Int,
                        var name: String) {

    companion object {
        fun empty() = Municipality(String.empty(), 0, String.empty())
    }
}