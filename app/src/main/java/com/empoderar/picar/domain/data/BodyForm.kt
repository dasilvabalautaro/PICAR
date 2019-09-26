package com.empoderar.picar.domain.data

import com.empoderar.picar.presentation.extension.empty

data class BodyForm(var id: Int,
                    var formId: Int,
                    var idProject: Int,
                    var code: String,
                    var value: String,
                    var description: String,
                    var satisfy: String,
                    var date: String,
                    var comment: String) {
    companion object{
        fun empty() = BodyForm(0, 0, 0, String.empty(),
                String.empty(), String.empty(), String.empty(),
                String.empty(), String.empty())
    }
}