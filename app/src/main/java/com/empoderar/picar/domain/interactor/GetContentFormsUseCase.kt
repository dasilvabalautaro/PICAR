package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.ContentForm
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.interfaces.ContentFormDataDao
import javax.inject.Inject


class GetContentFormsUseCase @Inject constructor(private val contentFormDataDao:
                                                 ContentFormDataDao):
        UseCase<List<ContentForm>, String>() {

    override suspend fun run(params: String): Either<Failure, List<ContentForm>> {
        return try {
            val listData = contentFormDataDao.getContentByTypeForm(params)
            val list = listData.map { ContentForm(it.id, it.frmId,
                    it.varTipo, it.varCodigo, it.description, it.valor,
                    it.logico, it.fecha, it.texto) }

            when(list.isNotEmpty()){
                true -> Either.Right(list)
                false -> Either.Left(Failure.DatabaseError())
            }

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }
    }

}