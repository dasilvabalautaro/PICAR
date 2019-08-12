package com.empoderar.picar.model.persistent.network.entity

import com.empoderar.picar.domain.data.Project

data class ProjectEntity(private var id: Int,
                         private var unity: Int,
                         private var codeProject: String,
                         private var nameProject: String,
                         private var latitude: Double,
                         private var longitude: Double,
                         private var state: String,
                         private var datePresentation: String,
                         private var dateInitAgreement: String,
                         private var dateEndAgreement: String) {
    fun toProject() = Project(id, unity, codeProject, nameProject, latitude,
            longitude, state, datePresentation, dateInitAgreement, dateEndAgreement)


}