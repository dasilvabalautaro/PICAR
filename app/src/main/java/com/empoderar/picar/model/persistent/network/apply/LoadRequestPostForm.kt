package com.empoderar.picar.model.persistent.network.apply


import com.empoderar.picar.domain.data.Message
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.entity.MessageEntity
import com.empoderar.picar.model.persistent.network.services.BodyRequestPostForm
import com.empoderar.picar.model.persistent.network.tools.LinkBackend
import com.empoderar.picar.presentation.plataform.NetworkHandler
import okhttp3.RequestBody
import javax.inject.Inject

interface LoadRequestPostForm {
    fun getResult(token: String, url: String, body: RequestBody): Either<Failure, Message>

    class SendRequest @Inject constructor(private val networkHandler: NetworkHandler,
                                          private val bodyRequest: BodyRequestPostForm):
            LoadRequestPostForm{
        override fun getResult(token: String, url: String, body: RequestBody): Either<Failure, Message> {
            return when (networkHandler.isConnected) {
                true -> LinkBackend.request(bodyRequest.send(token, url, body),
                        { it.toMessage() }, MessageEntity("", "", ""))
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }

    }
}