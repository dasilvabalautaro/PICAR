package com.empoderar.picar.domain.data

import com.empoderar.picar.presentation.extension.empty

data class ContentForm(var id: Int,
                       var frmId: String,
                       var varTipo: String,
                       var varCodigo: String,
                       var description: String,
                       var valor: Boolean,
                       var logico: Boolean,
                       var fecha: Boolean,
                       var texto: Boolean) {
    companion object {
        fun empty() = ContentForm(0, String.empty(), String.empty(),
                String.empty(), String.empty(), valor = false, logico = false,
                fecha = false, texto = false)
    }
}