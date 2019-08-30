package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.ContentForm
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.data.ContentFormData
import com.empoderar.picar.model.persistent.database.interfaces.ContentFormDataDao
import javax.inject.Inject

class InsertContentFormsUseCase @Inject constructor(private val contentFormDataDao:
                                                    ContentFormDataDao):
        UseCase<Boolean, List<ContentForm>>() {

    override suspend fun run(params: List<ContentForm>): Either<Failure, Boolean> {
        return try {
            val list = transformList(params)

            when (list.isNotEmpty()){
                true -> {
                    contentFormDataDao.insertContentForms(list)
                    Either.Right(true)
                }
                false -> Either.Left(Failure.DatabaseError())
            }

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }

    }


    private fun transformList(list: List<ContentForm>): List<ContentFormData>{
        val contents = ArrayList<ContentFormData>()

        if (list.isNotEmpty()){
            for (i in list.indices){
                val content = ContentFormData(list[i].id, list[i].frmId,
                        list[i].varTipo, list[i].varCodigo, list[i].description,
                        list[i].valor, list[i].logico, list[i].fecha, list[i].texto)
                contents.add(content)
            }
        }
        return contents.toList()
    }

}