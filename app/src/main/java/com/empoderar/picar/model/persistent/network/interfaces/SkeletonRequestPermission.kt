package com.empoderar.picar.model.persistent.network.interfaces

import com.empoderar.picar.model.persistent.network.entity.PermissionEntity
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

internal interface SkeletonRequestPermission {
    @POST
    fun send(@Url url: String, @Body body: RequestBody): Call<PermissionEntity>
}