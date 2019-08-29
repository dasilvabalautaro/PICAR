package com.empoderar.picar.model.persistent.network.interfaces

import com.empoderar.picar.model.persistent.network.entity.ContentFormEntity

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

internal interface SkeletonRequestContentForm {
    @GET
    fun acquire(@Header("Authorization") token: String,
                @Url url: String): Call<List<ContentFormEntity>>
}