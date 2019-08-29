package com.empoderar.picar.model.persistent.network.services

import com.empoderar.picar.model.persistent.network.interfaces.SkeletonRequestContentForm
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BodyRequestContentForm @Inject constructor(retrofit: Retrofit):
        SkeletonRequestContentForm {
    private val skeletonRequest by lazy {
        retrofit.create(SkeletonRequestContentForm::class.java) }

    override fun acquire(token: String,
                         url: String) =
            skeletonRequest.acquire(token, url)

}