package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.BodyForm
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.data.BodyFormData
import com.empoderar.picar.model.persistent.database.interfaces.BodyFormDataDao
import javax.inject.Inject

class InsertBodiesFormUseCase @Inject constructor(private val bodyFormDataDao:
                                                 BodyFormDataDao):
        UseCase<Boolean, List<BodyForm>>() {


    override suspend fun run(params: List<BodyForm>): Either<Failure, Boolean> {
        return try {
            val list = transformList(params)

            when (list.isNotEmpty()){
                true -> {
                    bodyFormDataDao.insertBodyForms(list)
                    Either.Right(true)
                }
                false -> Either.Left(Failure.DatabaseError())
            }

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }

    }

    private fun transformList(list: List<BodyForm>): List<BodyFormData>{
        val bodies = ArrayList<BodyFormData>()

        if (list.isNotEmpty()){
            for (i in list.indices){
                val body = BodyFormData(list[i].id, list[i].formId,
                        list[i].code, list[i].value, list[i].description,
                        list[i].satisfy, list[i].date, list[i].comment)
                bodies.add(body)
            }
        }
        return bodies.toList()
    }

}