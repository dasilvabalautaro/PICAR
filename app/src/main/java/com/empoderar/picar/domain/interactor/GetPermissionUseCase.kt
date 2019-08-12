package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Permission
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.apply.LoadRequestPermission
import com.empoderar.picar.model.persistent.network.tools.Conversion
import javax.inject.Inject


class GetPermissionUseCase @Inject constructor(private val loadRequestPermission:
                                               LoadRequestPermission):
        UseCase<Permission, List<String>>(){
    override suspend fun run(params: List<String>): Either<Failure, Permission> {

        val url = params[0]
        val body = Conversion.createRequestBody(params[1])
        return loadRequestPermission.getResult(url, body)
    }
}