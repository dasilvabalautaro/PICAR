package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Municipality
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.interfaces.MunicipalityDataDao
import javax.inject.Inject


class GetMunicipalitiesUseCase @Inject constructor(private val municipalityDataDao:
                                                   MunicipalityDataDao):
        UseCase<List<Municipality>, UseCase.None>(){

    override suspend fun run(params: None): Either<Failure, List<Municipality>> {
        return try {
            val listData = municipalityDataDao.getAll()
            val list = listData.map { Municipality(it.id, it.unit, it.name) }

            when(!list.isEmpty()){
                true -> Either.Right(list)
                false -> Either.Left(Failure.DatabaseError())
            }


        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }
    }

}