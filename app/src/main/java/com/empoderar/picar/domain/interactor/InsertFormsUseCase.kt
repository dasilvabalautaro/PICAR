package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure

import com.empoderar.picar.model.persistent.database.data.FormData
import com.empoderar.picar.model.persistent.database.interfaces.FormDataDao
import javax.inject.Inject

class InsertFormsUseCase @Inject constructor(private val formDataDao:
                                             FormDataDao):
        UseCase<Boolean, List<Form>>() {

    override suspend fun run(params: List<Form>): Either<Failure, Boolean> {
        return try {
            val list = transformList(params)

            when (list.isNotEmpty()){
                true -> {
                    formDataDao.insertForms(list)
                    Either.Right(true)
                }
                false -> Either.Left(Failure.DatabaseError())
            }

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }

    }

    private fun transformList(list: List<Form>): List<FormData>{
        val forms = ArrayList<FormData>()

        if (list.isNotEmpty()){
            for (i in list.indices){
                val form = FormData(list[i].id, list[i].project, list[i].frmId,
                        list[i].frmNro, list[i].title, list[i].dateEvaluation,
                        list[i].state, list[i].observation,
                        list[i].userMobile, list[i].latitude, list[i].longitude,
                        list[i].dateCreation, list[i].dateModification, 0)
                forms.add(form)
            }
        }
        return forms.toList()
    }

}