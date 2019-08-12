package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.apply.LoadRequestForm
import javax.inject.Inject

class GetFormsCloudUseCase @Inject constructor(
        private val loadRequestForm: LoadRequestForm):
        UseCase<List<Form>, List<String>>() {
    override suspend fun run(params: List<String>):
            Either<Failure, List<Form>> {
        val token = params[0]
        val url = params[1]
        return loadRequestForm.getResult(token, url)
    }

}