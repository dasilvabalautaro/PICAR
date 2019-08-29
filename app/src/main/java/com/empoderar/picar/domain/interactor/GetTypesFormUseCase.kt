package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.data.TypeForm
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.interfaces.TypeFormDataDao
import javax.inject.Inject

class GetTypesFormUseCase @Inject constructor(private val typeFormDataDao:
                                              TypeFormDataDao):
        UseCase<List<TypeForm>, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, List<TypeForm>> {
        return try {
            val listData = typeFormDataDao.getAll()
            val list = listData.map { TypeForm(it.frmID,
                    it.idStep, it.title, it.max, it.weigh,
                    it.observation, it.maxBit, it.valueBit, it.logicBit,
                    it.dateBit, it.textBit) }

            when(list.isNotEmpty()){
                true -> Either.Right(list)
                false -> Either.Left(Failure.DatabaseError())
            }


        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }
    }

}