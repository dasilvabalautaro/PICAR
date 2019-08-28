package com.empoderar.picar.model.persistent.network.entity

import com.empoderar.picar.domain.data.TypeForm

data class TypeFormEntity(private var frmId: String,
                          private var idEtapa: String,
                          private var titulo: String,
                          private var maximo: Double,
                          private var ponderacion: Double,
                          private var observation: String,
                          private var maxi: Boolean,
                          private var valor: Boolean,
                          private var logico: Boolean,
                          private var fecha: Boolean,
                          private var texto: Boolean) {
    fun toTypeForm() = TypeForm(frmId, idEtapa, titulo,
            maximo, ponderacion, observation, maxi, valor,
            logico, fecha, texto)
}