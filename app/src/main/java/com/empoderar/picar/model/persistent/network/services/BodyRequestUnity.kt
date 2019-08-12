package com.empoderar.picar.model.persistent.network.services


import com.empoderar.picar.model.persistent.network.interfaces.SkeletonRequestUnity
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BodyRequestUnity @Inject constructor(retrofit: Retrofit):
        SkeletonRequestUnity {
    private val skeletonRequest by lazy {
        retrofit.create(SkeletonRequestUnity ::class.java) }

    override fun acquire(token: String, url: String) = skeletonRequest.acquire(token, url)
}