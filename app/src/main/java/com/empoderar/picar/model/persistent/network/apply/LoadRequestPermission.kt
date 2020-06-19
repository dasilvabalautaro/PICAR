package com.empoderar.picar.model.persistent.network.apply

import com.empoderar.picar.domain.data.Permission
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.entity.PermissionEntity
import com.empoderar.picar.model.persistent.network.services.BodyRequestPermission
import com.empoderar.picar.model.persistent.network.tools.LinkBackend
import com.empoderar.picar.presentation.plataform.NetworkHandler
import okhttp3.RequestBody
import javax.inject.Inject

interface LoadRequestPermission {
    fun getResult(url: String, body: RequestBody): Either<Failure, Permission>

    class SendRequest @Inject constructor(private val networkHandler: NetworkHandler,
                                          private val bodyRequest: BodyRequestPermission):
            LoadRequestPermission{
        override fun getResult(url: String, body: RequestBody): Either<Failure, Permission> {
            return when (networkHandler.isConnected) {
                true -> LinkBackend.request(bodyRequest.send(url, body),
                        { it.toPermission() },
                        PermissionEntity("", "", "", 0, 0))
                false -> Either.Left(Failure.NetworkConnection())
            }
        }

    }
}