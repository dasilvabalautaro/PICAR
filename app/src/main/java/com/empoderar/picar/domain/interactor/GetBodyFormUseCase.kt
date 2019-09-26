package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.BodyForm
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.interfaces.BodyFormDataDao
import javax.inject.Inject

class GetBodyFormUseCase @Inject constructor(private val bodyFormDataDao:
                                             BodyFormDataDao):
        UseCase<List<BodyForm>, Int>() {
    override suspend fun run(params: Int): Either<Failure, List<BodyForm>> {
        return try {
            val listData = bodyFormDataDao.getBodyByIdForm(params)
            val list = listData.map { BodyForm(it.id, it.formId,
                    it.idProject, it.code, it.value, it.description, it.satisfy,
                    it.date, it.comment) }

            when(list.isNotEmpty()){
                true -> Either.Right(list)
                false -> Either.Left(Failure.DatabaseError())
            }

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }
    }

}