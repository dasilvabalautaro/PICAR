package com.empoderar.picar.model.persistent.network.interfaces

import io.reactivex.Observable
import retrofit2.http.GET

interface PicarWebService {
    @GET("Hitest")
    fun getPublicEvents(): Observable<String>
}