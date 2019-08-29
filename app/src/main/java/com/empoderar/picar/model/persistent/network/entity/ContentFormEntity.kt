package com.empoderar.picar.model.persistent.network.entity

import com.empoderar.picar.domain.data.ContentForm

data class ContentFormEntity(private var id: Int,
                             private var frmId: String,
                             private var varTipo: String,
                             private var varCodigo: String,
                             private var description: String,
                             private var valor: Boolean,
                             private var logico: Boolean,
                             private var fecha: Boolean,
                             private var texto: Boolean) {
    fun toContentForm() = ContentForm(id, frmId, varTipo,
            varCodigo, description, valor, logico, fecha, texto)
}