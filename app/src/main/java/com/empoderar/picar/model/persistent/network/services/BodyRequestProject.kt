package com.empoderar.picar.model.persistent.network.services

import com.empoderar.picar.model.persistent.network.interfaces.SkeletonRequestProject
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BodyRequestProject @Inject constructor(retrofit: Retrofit):
        SkeletonRequestProject{
    private val skeletonRequest by lazy {
        retrofit.create(SkeletonRequestProject ::class.java) }
    override fun acquire(token: String, url: String) = skeletonRequest.acquire(token, url)
}