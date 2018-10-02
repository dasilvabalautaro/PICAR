package com.empoderar.picar.model.persistent.network.repository

import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.services.UsersService
import com.empoderar.picar.presentation.data.User
import com.empoderar.picar.presentation.plataform.NetworkHandler
import retrofit2.Call
import javax.inject.Inject

interface UsersRepository {
    fun users(): Either<Failure, List<User>>

    class UsersNetwork @Inject constructor(private val networkHandler: NetworkHandler,
                                          private val service: UsersService): UsersRepository{

        override fun users(): Either<Failure, List<User>> {
            return when (networkHandler.isConnected) {
                true -> request(service.users(), { it.map { it.toUser() } }, emptyList())
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T):
                Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform((response.body() ?: default)))
                    false -> Either.Left(Failure.ServerError())
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError())
            }
        }
    }
}