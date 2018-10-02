package com.empoderar.picar.model.persistent.network.interfaces

import com.empoderar.picar.model.persistent.network.entity.UserEntity
import retrofit2.Call
import retrofit2.http.GET

internal interface UserApi {
    companion object {
        private const val users = "users.json"

    }

    @GET(users) fun users(): Call<List<UserEntity>>
}