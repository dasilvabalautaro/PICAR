package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Message
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.apply.LoadRequestPostForm
import com.empoderar.picar.model.persistent.network.entity.MessageEntity
import com.empoderar.picar.model.persistent.network.tools.Conversion
import javax.inject.Inject

class UploadFormUseCase @Inject constructor(private val loadRequestPostForm:
                                            LoadRequestPostForm):
        UseCase<Message, List<String>>() {
    override suspend fun run(params: List<String>): Either<Failure, Message> {

        val token = params[0]
        val url = params[1]
        val body = Conversion.createRequestBody(params[2])
        return loadRequestPostForm.getResult(token, url, body)
    }

}