package com.empoderar.picar.model.persistent.network.entity

import com.empoderar.picar.domain.data.Form
import java.util.*

data class FormEntity(private var id: String,
                      private var unit: Int,
                      private var user: Int,
                      private var dateForm: Date?,
                      private var lat: Double,
                      private var lon: Double,
                      private var variable1: Double,
                      private var variable2: Int,
                      private var variable3: Date?,
                      private var variable4: Boolean,
                      private var comment1: String,
                      private var comment2: String,
                      private var comment3: String,
                      private var updateDate: Date?,
                      private var updateUser: Int) {

    fun toForm() = Form(id, unit, user, dateForm, lat, lon,
            variable1, variable2, variable3, variable4, comment1,
            comment2, comment3, updateDate, updateUser)
}