package com.empoderar.picar.model.persistent.network.apply

import com.empoderar.picar.domain.data.ContentForm
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.services.BodyRequestContentForm
import com.empoderar.picar.model.persistent.network.tools.LinkBackend
import com.empoderar.picar.presentation.plataform.NetworkHandler
import javax.inject.Inject

interface LoadRequestContentForm {
    fun getResult(token: String, url: String): Either<Failure, List<ContentForm>>

    class SendRequest @Inject constructor(private val networkHandler: NetworkHandler,
                                          private val bodyRequest: BodyRequestContentForm):
            LoadRequestContentForm{
        override fun getResult(token: String, url: String):
                Either<Failure, List<ContentForm>> {
            return when (networkHandler.isConnected) {
                true -> LinkBackend.request(bodyRequest.acquire(token, url),
                        { it.map { it.toContentForm() } }, emptyList())
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }

    }

}