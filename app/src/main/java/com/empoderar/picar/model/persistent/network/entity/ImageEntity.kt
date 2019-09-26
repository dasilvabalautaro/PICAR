package com.empoderar.picar.model.persistent.network.entity

data class ImageEntity(private var idFoto: Int,
                       private var frmNro: Int,
                       private var project: Int,
                       private var base64: String,
                       private var latitudeMobile: Double,
                       private var longitudeMobile: Double,
                       private var dateCreation: String)