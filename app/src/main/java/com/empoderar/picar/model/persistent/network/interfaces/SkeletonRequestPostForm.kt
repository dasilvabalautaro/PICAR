package com.empoderar.picar.model.persistent.network.interfaces


import com.empoderar.picar.model.persistent.network.entity.MessageEntity
import retrofit2.Call
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

internal interface SkeletonRequestPostForm {
    @POST
    fun send(@Header("Authorization") token: String,
             @Url url: String, @Body body: RequestBody): Call<MessageEntity>
}