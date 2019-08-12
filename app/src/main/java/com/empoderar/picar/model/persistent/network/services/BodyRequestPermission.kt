package com.empoderar.picar.model.persistent.network.services

import com.empoderar.picar.model.persistent.network.interfaces.SkeletonRequestPermission
import okhttp3.RequestBody
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BodyRequestPermission @Inject constructor(retrofit: Retrofit):
        SkeletonRequestPermission {

    private val skeletonRequest by lazy {
        retrofit.create(SkeletonRequestPermission::class.java) }

    override fun send(url: String, body: RequestBody) = skeletonRequest.send(url, body)

}