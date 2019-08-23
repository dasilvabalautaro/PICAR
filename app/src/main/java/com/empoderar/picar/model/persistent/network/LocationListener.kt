package com.empoderar.picar.model.persistent.network

import android.content.Context
import android.location.Location
import android.os.Bundle
import com.empoderar.picar.App
import com.empoderar.picar.di.ApplicationComponent
import com.empoderar.picar.model.observer.LocationChangeObserver
import com.empoderar.picar.model.persistent.caching.Variables
import javax.inject.Inject

class LocationListener (provider: String, private val context: Context):
        android.location.LocationListener {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (context.applicationContext as App).appComponent
    }

    private var lastLocation: Location? = null

    @Inject
    lateinit var locationChangeObserver: LocationChangeObserver

    init {
        appComponent.inject(this)
        lastLocation = Location(provider)
    }

    override fun onLocationChanged(location: Location) {
        println("onLocationChanged: $location")
        lastLocation!!.set(location)

        locationChangeObserver.location = lastLocation
        locationChangeObserver.observableLocation.onNext(locationChangeObserver
                .location!!)
        Variables.locationUser.lat =  lastLocation!!.latitude
        Variables.locationUser.lon = lastLocation!!.longitude
        println("LastLocation: " + lastLocation!!.latitude.toString() +
                "  " + lastLocation!!.longitude.toString())
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