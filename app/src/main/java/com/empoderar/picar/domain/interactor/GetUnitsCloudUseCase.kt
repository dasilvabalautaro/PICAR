package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Unity
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.apply.LoadRequestUnity
import javax.inject.Inject

class GetUnitsCloudUseCase @Inject constructor(private val loadRequestUnity:
                                               LoadRequestUnity):
        UseCase<List<Unity>, List<String>>() {
    override suspend fun run(params: List<String>): Either<Failure, List<Unity>> {

        val token = params[0]
        val url = params[1]
        return loadRequestUnity.getResult(token, url)
    }
}