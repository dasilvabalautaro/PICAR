package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.presentation.data.ProjectView
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.GetProjectsDatabaseViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapProjectsFragment: BaseFragment(), OnMapReadyCallback {
    private lateinit var googleMaps: GoogleMap
    private lateinit var getProjectsViewModel: GetProjectsDatabaseViewModel
    private val listCoordinates = ArrayList<LatLng>()
    private val listNameProjects = ArrayList<String>()
    override fun layoutId()= R.layout.view_map

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        getProjectsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetProjects)
            failure(failure, ::handleFailure)
        }
        loadProjectList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val supportMapFragment = childFragmentManager
                .findFragmentById(R.id.map_view) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }

    private fun loadProjectList(){
        getProjectsViewModel.loadProjects()

    }

    private fun handleGetProjects(list: List<ProjectView>?){

        listProject = list.orEmpty()
        if (!listProject!!.isNullOrEmpty()){
            for (projectView: ProjectView in listProject!!){
                val lat = projectView.latitude
                val lng = projectView.longitude
                val name = projectView.nameProject
                val coordinates = LatLng(lat, lng)
                this.listCoordinates.add(coordinates)
                this.listNameProjects.add(name)
            }
        }

    }

    override fun renderFailure(message: Int) {
        notify(message)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMaps = googleMap!!
        googleMap.uiSettings.isZoomControlsEnabled = true

        for (i in 0 until this.listCoordinates.count()){
            googleMaps.addMarker(MarkerOptions()
                    .position(this.listCoordinates[i])
                    .title(this.listNameProjects[i]))

        }

        googleMaps.moveCamera(CameraUpdateFactory
                .newLatLngZoom(this.listCoordinates[0], 10f))

    }
    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}