package com.empoderar.picar.domain.data

import com.empoderar.picar.presentation.extension.empty

data class TypeForm(var frmId: String,
                    var idEtapa: String,
                    var titulo: String,
                    var maximo: Double,
                    var ponderacion: Double,
                    var observation: String,
                    var maxi: Boolean,
                    var valor: Boolean,
                    var logico: Boolean,
                    var fecha: Boolean,
                    var texto: Boolean) {
    companion object{
        fun empty() = TypeForm(String.empty(), String.empty(),
                String.empty(), 0.0, 0.0, String.empty(),
                maxi = false, valor = false, logico = false,
                fecha = false, texto = false)
    }
}