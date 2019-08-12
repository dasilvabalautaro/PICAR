package com.empoderar.picar.model.persistent.network.entity

import com.empoderar.picar.domain.data.Form

data class FormEntity(private var id: Int,
                      private var project: Int,
                      private var frmId: String,
                      private var frmNro: Int,
                      private var titulo: String,
                      private var dateEvaluation: String,
                      private var state: Int,
                      private var observation: String?,
                      private var userMobile: Int,
                      private var latitudeMobile: Double,
                      private var longitudeMobile: Double,
                      private var dateCreation: String,
                      private var dateModification: String) {

    fun toForm() = Form(id, project, frmId, frmNro, titulo, dateEvaluation,
            state, observation, userMobile, latitudeMobile, longitudeMobile,
            dateCreation, dateModification)
}