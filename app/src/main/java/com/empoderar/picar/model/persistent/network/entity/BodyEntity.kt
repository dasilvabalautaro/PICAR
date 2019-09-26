package com.empoderar.picar.model.persistent.network.entity

data class BodyEntity(private var id: Int,
                      private var formId: Int,
                      private var project: Int,
                      private var code: String,
                      private var value: String,
                      private var description: String,
                      private var satisfy: String,
                      private var date: String,
                      private var comment: String) {
}