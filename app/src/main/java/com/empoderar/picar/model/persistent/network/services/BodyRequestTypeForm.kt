package com.empoderar.picar.model.persistent.network.services

import com.empoderar.picar.model.persistent.network.interfaces.SkeletonRequestTypeForm
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BodyRequestTypeForm @Inject constructor(retrofit: Retrofit):
        SkeletonRequestTypeForm {
    private val skeletonRequest by lazy {
        retrofit.create(SkeletonRequestTypeForm ::class.java) }

    override fun acquire(token: String,
                         url: String) =
            skeletonRequest.acquire(token, url)
}