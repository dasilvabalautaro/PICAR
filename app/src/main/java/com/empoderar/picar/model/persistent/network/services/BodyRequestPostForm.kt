package com.empoderar.picar.model.persistent.network.services

import com.empoderar.picar.model.persistent.network.interfaces.SkeletonRequestPostForm
import okhttp3.RequestBody
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BodyRequestPostForm @Inject constructor(retrofit: Retrofit):
        SkeletonRequestPostForm {

    private val skeletonRequest by lazy {
        retrofit.create(SkeletonRequestPostForm::class.java) }
    override fun send(token: String, url: String, body: RequestBody) =
            skeletonRequest.send(token, url, body)
}