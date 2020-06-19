package com.empoderar.picar.presentation.plataform

import android.content.Context
import com.empoderar.picar.presentation.extension.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler
@Inject constructor(private val context: Context) {
    val isConnected get() = context.networkInfo != null
}