package com.empoderar.picar.model.persistent.network.apply

import com.empoderar.picar.domain.data.TypeForm
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.services.BodyRequestTypeForm
import com.empoderar.picar.model.persistent.network.tools.LinkBackend
import com.empoderar.picar.presentation.plataform.NetworkHandler
import javax.inject.Inject


interface LoadRequestTypeForm {
    fun getResult(token: String, url: String): Either<Failure, List<TypeForm>>
    class SendRequest @Inject constructor(private val networkHandler: NetworkHandler,
                                          private val bodyRequest: BodyRequestTypeForm):
            LoadRequestTypeForm{
        override fun getResult(token: String, url: String):
                Either<Failure, List<TypeForm>> {
            return when (networkHandler.isConnected) {
                true -> LinkBackend.request(bodyRequest.acquire(token, url),
                        { it.map { it.toTypeForm() } }, emptyList())
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }

    }

}