package com.empoderar.picar.model.persistent.network.services

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent

@Suppress("DEPRECATION")
object ToolService {

    private fun checkServiceLocationRunning(activity: Activity): Boolean {
        val manager = activity.getSystemService(Context
                .ACTIVITY_SERVICE) as ActivityManager
        manager.getRunningServices(Integer.MAX_VALUE).forEach { service ->
            when {
                "com.empoderar.picar.model.persistent.network.services.LocationService" == service.service.className -> return true
            }
        }
        return false
    }

    fun runLocationService(activity: Activity){
        if (!checkServiceLocationRunning(activity)){
            val intent = Intent(activity, LocationService::class.java)
            activity.startService(intent)
        }
    }

}