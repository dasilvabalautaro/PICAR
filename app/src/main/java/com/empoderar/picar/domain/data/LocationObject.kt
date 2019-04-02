package com.empoderar.picar.domain.data

data class LocationObject(var lat: Double,
                          var lon: Double,
                          var title: String) {
    companion object {
        fun empty() = LocationObject(0.0, 0.0, "")
    }
}