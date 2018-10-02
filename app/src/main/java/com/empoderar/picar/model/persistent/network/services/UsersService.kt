package com.empoderar.picar.model.persistent.network.services

import com.empoderar.picar.model.persistent.network.interfaces.UserApi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersService @Inject constructor(retrofit: Retrofit): UserApi {

    private val usersApi by lazy { retrofit.create(UserApi::class.java) }

    override fun users() = usersApi.users()

}