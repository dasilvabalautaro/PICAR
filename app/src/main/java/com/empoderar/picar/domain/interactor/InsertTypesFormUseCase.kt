package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.TypeForm
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.data.TypeFormData
import com.empoderar.picar.model.persistent.database.interfaces.TypeFormDataDao
import javax.inject.Inject

class InsertTypesFormUseCase @Inject constructor(private val typeFormDataDao:
                                                 TypeFormDataDao):
    UseCase<Boolean, List<TypeForm>>(){

    override suspend fun run(params: List<TypeForm>): Either<Failure, Boolean> {
        return try {
            val list = transformList(params)

            when (list.isNotEmpty()){
                true -> {
                    typeFormDataDao.insertTypesForm(list)
                    Either.Right(true)
                }
                false -> Either.Left(Failure.DatabaseError())
            }

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }

    }

    private fun transformList(list: List<TypeForm>): List<TypeFormData>{
        val typesForm = ArrayList<TypeFormData>()

        if (list.isNotEmpty()){
            for (i in list.indices){
                val type = TypeFormData(list[i].frmId, list[i].idEtapa,
                        list[i].titulo, list[i].maximo, list[i].ponderacion,
                        list[i].observation, list[i].maxi, list[i].valor,
                        list[i].logico, list[i].fecha, list[i].texto)
                typesForm.add(type)
            }
        }
        return typesForm.toList()
    }

}