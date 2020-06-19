package com.empoderar.picar.model.persistent.network.apply

import com.empoderar.picar.domain.data.Unity
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.services.BodyRequestUnity
import com.empoderar.picar.model.persistent.network.tools.LinkBackend
import com.empoderar.picar.presentation.plataform.NetworkHandler
import javax.inject.Inject

interface LoadRequestUnity {
    fun getResult(token: String, url: String): Either<Failure, List<Unity>>

    class SendRequest @Inject constructor(private val networkHandler: NetworkHandler,
                                          private val bodyRequest: BodyRequestUnity):
            LoadRequestUnity{
        override fun getResult(token: String, url: String): Either<Failure, List<Unity>> {
            return when (networkHandler.isConnected) {
                true -> LinkBackend.request(bodyRequest.acquire(token, url),
                        { it.map { it.toUnity() } }, emptyList())
                false  -> Either.Left(Failure.NetworkConnection())
            }
        }

    }
}