package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.interfaces.FormDataDao
import javax.inject.Inject

class UpdateUploadFormUseCase @Inject constructor(private val formDataDao:
                                                   FormDataDao):
        UseCase<Boolean, List<Int>>() {
    override suspend fun run(params: List<Int>): Either<Failure, Boolean> {
        return try {
            formDataDao.updateUpload(params[0], params[1])
            Either.Right(true)

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }

    }
}