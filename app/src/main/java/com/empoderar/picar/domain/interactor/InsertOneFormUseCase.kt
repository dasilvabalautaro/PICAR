package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.data.FormData
import com.empoderar.picar.model.persistent.database.interfaces.FormDataDao
import javax.inject.Inject

class InsertOneFormUseCase @Inject constructor(private val formDataDao:
                                               FormDataDao):
        UseCase<Long, Form>() {

    override suspend fun run(params: Form): Either<Failure, Long> {
        return try {
            val form = transform(params)

            val id = formDataDao.insert(form)
            Either.Right(id)

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }

    }


    private fun transform(form: Form): FormData{
        return FormData(id = form.id, project = form.project, frmId = form.frmId,
                frmNro = form.frmNro, title = form.title, dateEvaluation = form.dateEvaluation,
                state = form.state, observation = form.observation,
                userMobile = form.userMobile, latitude = form.latitude, longitude = form.longitude,
                dateCreation = form.dateCreation, dateModification = form.dateModification)
    }
}