package com.empoderar.picar.presentation.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.empoderar.picar.R
import com.empoderar.picar.model.observer.LocationChangeObserver
import com.empoderar.picar.model.persistent.caching.Variables
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MapFragment: BaseFragment(), OnMapReadyCallback {
    private lateinit var googleMaps: GoogleMap
    private lateinit var markMap: Marker

    @Inject
    lateinit var locationChangeObserver: LocationChangeObserver

    private var disposableImage: Disposable? = null

    override fun layoutId() = R.layout.view_map

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        val hearLocation = locationChangeObserver.observableLocation.map { l -> l }
        disposableImage = hearLocation .observeOn(AndroidSchedulers.mainThread())
                .subscribe { l ->
                    kotlin.run {
                        val lat = l.latitude
                        val long = l.longitude
                        val coordinates = LatLng(lat, long)
                        this.markMap.position = coordinates

                    }
                }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val supportMapFragment = childFragmentManager
                .findFragmentById(R.id.map_view) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }
    override fun renderFailure(message: Int) {
        notify(message)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        googleMaps = googleMap!!
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true

        val lat = Variables.locationUser.lat
        val lng = Variables.locationUser.lon
        val coordinates = LatLng(lat, lng)
        markMap =  googleMaps.addMarker(MarkerOptions().position(coordinates)
                .title(getString(R.string.title_my_location)))
        googleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f))

    }

    override fun onDestroy() {
        super.onDestroy()
        disposableImage!!.dispose()
    }
}