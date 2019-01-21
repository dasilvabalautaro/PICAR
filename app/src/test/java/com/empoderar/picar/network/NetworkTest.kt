package com.empoderar.picar.network

import com.empoderar.picar.AndroidTest
import com.empoderar.picar.presentation.plataform.NetworkHandler
import junit.framework.Assert
import org.junit.Before
import org.junit.Test


class NetworkTest : AndroidTest() {
    private lateinit var networkHandler: NetworkHandler
    private val context = context()

    @Before fun setup(){
        networkHandler = NetworkHandler(context)
    }

    @Test
    fun isConnect(){
        val connect = networkHandler.isConnected

        Assert.assertEquals("Connect true",true, connect)
    }

}