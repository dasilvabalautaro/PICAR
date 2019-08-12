package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Unity
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.data.UnityData
import com.empoderar.picar.model.persistent.database.interfaces.UnityDataDao
import javax.inject.Inject

class InsertUnitsUseCase @Inject constructor(private val unityDataDao: UnityDataDao):
        UseCase<Boolean, List<Unity>>() {
    override suspend fun run(params: List<Unity>): Either<Failure, Boolean> {
        return try {
            val list = transformList(params)

            when (list.isNotEmpty()){
                true -> {
                    unityDataDao.insertUnities(list)
                    Either.Right(true)
                }
                false -> Either.Left(Failure.DatabaseError())
            }

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }

    }

    private fun transformList(list: List<Unity>): List<UnityData>{
        val unities = ArrayList<UnityData>()

        if (list.isNotEmpty()){
            for (i in list.indices){
                val unit = UnityData(list[i].id, list[i].name, list[i].short)
                unities.add(unit)
            }
        }
        return unities.toList()
    }

}