package com.empoderar.picar.model.persistent.network.services

import com.empoderar.picar.model.persistent.network.interfaces.SkeletonRequestForm
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BodyRequestForm @Inject constructor(retrofit: Retrofit):
        SkeletonRequestForm {
    private val skeletonRequest by lazy {
        retrofit.create(SkeletonRequestForm ::class.java) }

    override fun acquire(token: String,
                         url: String) = skeletonRequest.acquire(token, url)
}