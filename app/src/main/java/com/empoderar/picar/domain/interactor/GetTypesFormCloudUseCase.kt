package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.TypeForm
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.apply.LoadRequestTypeForm
import javax.inject.Inject

class GetTypesFormCloudUseCase @Inject constructor(private val loadRequestTypeForm:
                                                   LoadRequestTypeForm):
        UseCase<List<TypeForm>, List<String>>() {

    override suspend fun run(params: List<String>):
            Either<Failure, List<TypeForm>> {
        val token = params[0]
        val url = params[1]
        return loadRequestTypeForm.getResult(token, url)
    }

}