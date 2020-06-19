package com.empoderar.picar.model.persistent.network.apply

import com.empoderar.picar.domain.data.Project
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.services.BodyRequestProject
import com.empoderar.picar.model.persistent.network.tools.LinkBackend
import com.empoderar.picar.presentation.plataform.NetworkHandler
import javax.inject.Inject

interface LoadRequestProject {
    fun getResult(token: String, url: String): Either<Failure, List<Project>>

    class SendRequest @Inject constructor(private val networkHandler: NetworkHandler,
                                          private val bodyRequest: BodyRequestProject):
            LoadRequestProject{
        override fun getResult(token: String, url: String):
                Either<Failure, List<Project>> {
            return when (networkHandler.isConnected) {
                true -> LinkBackend.request(bodyRequest.acquire(token, url),
                        { it.map { it.toProject() } }, emptyList())
                false  -> Either.Left(Failure.NetworkConnection())
            }
        }

    }
}