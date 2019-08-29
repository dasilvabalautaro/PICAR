package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.ContentForm
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.apply.LoadRequestContentForm
import javax.inject.Inject

class GetContentFormsCloudUseCase @Inject constructor(private val loadRequestContentForm:
                                                      LoadRequestContentForm):
        UseCase<List<ContentForm>, List<String>>() {

    override suspend fun run(params: List<String>):
            Either<Failure, List<ContentForm>> {
        val token = params[0]
        val url = params[1]
        return loadRequestContentForm.getResult(token, url)
    }

}