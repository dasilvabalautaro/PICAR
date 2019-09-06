package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.interfaces.FormDataDao
import java.util.*
import javax.inject.Inject

class GetFormsUseCase @Inject constructor(private val formDataDao:
                                          FormDataDao):
        UseCase<List<Form>, Int>() {

    override suspend fun run(params: Int): Either<Failure, List<Form>> {
        return try {
            val listData = formDataDao.getAll(params)
            val list = listData.map { Form(it.id, it.project, it.frmId,
                    it.frmNro, it.title, it.dateEvaluation, it.state, it.observation,
                    it.userMobile, it.latitude, it.longitude, it.dateCreation,
                    it.dateModification) }

            when(list.isNotEmpty()){
                true -> Either.Right(list)
                false -> Either.Left(Failure.DatabaseError())
            }


        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }
    }

}