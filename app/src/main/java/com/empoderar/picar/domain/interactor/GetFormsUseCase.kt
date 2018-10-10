package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.interfaces.FormDataDao
import java.util.*
import javax.inject.Inject

class GetFormsUseCase @Inject constructor(private val formDataDao:
                                          FormDataDao):
        UseCase<List<Form>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Form>> {
        return try {
            val listData = formDataDao.getAll()
            val list = listData.map { Form(it.id, it.unit, it.user,
                    Date(it.dateForm?:0), it.lat, it.lon, it.variable1, it.variable2,
                    Date(it.variable3?:0), it.variable4, it.comment1, it.comment2,
                    it.comment3, Date(it.updateDate?:0), it.updateUser) }

            when(!list.isEmpty()){
                true -> Either.Right(list)
                false -> Either.Left(Failure.DatabaseError())
            }


        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }
    }

}