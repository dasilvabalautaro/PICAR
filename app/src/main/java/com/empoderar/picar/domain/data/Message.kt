package com.empoderar.picar.domain.data

import com.empoderar.picar.presentation.extension.empty

data class Message(var succes: String,
                   var error: String,
                   var message: String) {
    companion object{
        fun empty() = Message(String.empty(), String.empty(), String.empty())
    }
}