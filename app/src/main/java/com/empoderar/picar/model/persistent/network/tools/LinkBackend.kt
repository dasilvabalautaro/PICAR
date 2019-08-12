package com.empoderar.picar.model.persistent.network.tools

import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import retrofit2.Call

object LinkBackend {
    inline fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T):
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