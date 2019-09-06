package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.interfaces.FormDataDao
import javax.inject.Inject

class UpdateUploadAllFormsUseCase @Inject constructor(private val formDataDao:
                                                      FormDataDao):
        UseCase<Boolean, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, Boolean> {
        return try {
            formDataDao.updateAllUpload()
            Either.Right(true)

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }

    }

}