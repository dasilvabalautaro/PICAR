package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.data.UserData
import com.empoderar.picar.model.persistent.database.interfaces.UserDataDao
import com.empoderar.picar.presentation.data.UserView
import javax.inject.Inject

class InsertUsersUseCase @Inject constructor(private val userDataDao:
                                             UserDataDao):
        UseCase<Boolean, List<UserView>>() {

    override suspend fun run(params: List<UserView>): Either<Failure, Boolean> {
        return try {
            val list = transformList(params)

            when (list.isNotEmpty()){
                true -> {
                    userDataDao.insertUsers(list)
                    Either.Right(true)
                }
                false -> Either.Left(Failure.DatabaseError())
            }

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }
    }

    private fun transformList(list: List<UserView>): List<UserData>{
        val users = ArrayList<UserData>()

        if (list.isNotEmpty()){
            for (i in list.indices){
                val user = list[i].name?.let {
                    list[i].phone?.let { it1 ->
                        list[i].address?.let { it2 ->
                            UserData(list[i].id, list[i].unit, list[i].role,
                                    it, it1, it2)
                        }
                    }
                }
                if (user != null) {
                    users.add(user)
                }
            }
        }
        return users.toList()
    }
}