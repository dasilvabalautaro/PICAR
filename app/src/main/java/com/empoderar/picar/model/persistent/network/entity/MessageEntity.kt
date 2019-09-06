package com.empoderar.picar.model.persistent.network.entity

import com.empoderar.picar.domain.data.Message

data class MessageEntity(private var succes: String,
                         private var error: String,
                         private var message: String) {
    fun toMessage() = Message(succes, error, message)
}