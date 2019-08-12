package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Unity
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.interfaces.UnityDataDao
import javax.inject.Inject

class GetUnitsDatabaseUseCase @Inject constructor(private val unityDataDao:
                                          UnityDataDao):
        UseCase<List<Unity>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Unity>> {
        return try {

            val listData = unityDataDao.getAll()
            val list = listData.map { Unity(it.id, it.name,
                    it.short) }

            when(list.isNotEmpty()){
                true -> Either.Right(list)
                false -> Either.Left(Failure.DatabaseError())
            }


        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }
    }

}