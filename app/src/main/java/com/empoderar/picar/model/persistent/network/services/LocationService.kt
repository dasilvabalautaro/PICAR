package com.empoderar.picar.model.persistent.network.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import com.empoderar.picar.model.persistent.preference.PreferenceRepository
import com.empoderar.picar.model.persistent.preference.PreferenceRepository.set

class LocationService: Service() {

    internal val context = applicationContext!!

    private var locationManager: LocationManager? = null

    private var locationListeners = arrayOf(LocationListener(LocationManager
            .GPS_PROVIDER, context), LocationListener(LocationManager.NETWORK_PROVIDER, context))

    class LocationListener(provider: String, private val context: Context) : android.location.LocationListener {
        private var lastLocation: Location

        init {
            println("LocationListener $provider")
            lastLocation = Location(provider)
        }

        override fun onLocationChanged(location: Location) {
            println("onLocationChanged: $location")
            lastLocation.set(location)
            val prefs = PreferenceRepository.customPrefs(context,
                    PreferenceRepository.preferenceApp)
            prefs[PreferenceRepository.latitude] = lastLocation.latitude.toString()
            prefs[PreferenceRepository.longitude] = lastLocation.longitude.toString()
            println("LastLocation: " + lastLocation.latitude.toString() + 
                    "  " + lastLocation.longitude.toString())
        }

        override fun onProviderDisabled(provider: String) {
            println("onProviderDisabled: $provider")
        }

        override fun onProviderEnabled(provider: String) {
            println("onProviderEnabled: $provider")
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            println("onStatusChanged: $provider")
        }
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("onStartCommand")
        super.onStartCommand(intent, flags, startId)
        return Service.START_STICKY
    }

    override fun onCreate() {
        println("onCreate")

        initializeLocationManager()
        try {
            locationManager!!.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 
                    locationInterval.toLong(), locationDistance,
                    locationListeners[1])
        } catch (ex: SecurityException) {
            println("fail to request location update, ignore $ex")
        } catch (ex: IllegalArgumentException) {
            println("network provider does not exist, ${ex.message}")
        }

        try {
            locationManager!!.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 
                    locationInterval.toLong(), locationDistance,
                    locationListeners[0])
        } catch (ex: SecurityException) {
            println("fail to request location update, ignore: $ex")
        } catch (ex: IllegalArgumentException) {
            println("gps provider does not exist ${ex.message}")
        }

    }

    override fun onDestroy() {
        println("onDestroy")
        super.onDestroy()
        if (locationManager != null) {
            for (i in locationListeners.indices) {
                try {
                    locationManager!!.removeUpdates(locationListeners[i])
                } catch (ex: Exception) {
                    println("Fail to remove location listeners, ignore: $ex")
                }

            }
        }
    }

    private fun initializeLocationManager() {
        println("initializeLocationManager")
        if (locationManager == null) {
            locationManager = applicationContext
                    .getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
    }

    companion object {
        private const val locationInterval = 1000
        private const val locationDistance = 5f
    }

}