package com.empoderar.picar.model.persistent.network.apply

import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.data.Project
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.services.BodyRequestForm
import com.empoderar.picar.model.persistent.network.tools.LinkBackend
import com.empoderar.picar.presentation.plataform.NetworkHandler
import javax.inject.Inject

interface LoadRequestForm {
    fun getResult(token: String, url: String): Either<Failure, List<Form>>

    class SendRequest @Inject constructor(private val networkHandler: NetworkHandler,
                                          private val bodyRequest: BodyRequestForm):
            LoadRequestForm{
        override fun getResult(token: String, url: String):
                Either<Failure, List<Form>> {
            return when (networkHandler.isConnected) {
                true -> LinkBackend.request(bodyRequest.acquire(token, url),
                        { it.map { it.toForm() } }, emptyList())
                false -> Either.Left(Failure.NetworkConnection())
            }
        }

    }
}