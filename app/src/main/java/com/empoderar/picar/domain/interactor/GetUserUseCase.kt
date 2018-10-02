package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.domain.functional.HashUtils
import com.empoderar.picar.model.persistent.database.data.UserData
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.interfaces.UserDataDao
import javax.inject.Inject


class GetUserUseCase @Inject constructor(private val userDataDao:
                                         UserDataDao):
        UseCase<UserData, List<String>>() {

    override suspend fun run(params: List<String>): Either<Failure, UserData> {
        return try {
            val list = userDataDao.findUserByName(params[0])
            val user = verifyPassword(list, params[1])
            when (user != null){
                true -> Either.Right(user!!)
                false -> Either.Left(Failure.DatabaseError())
            }

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }
    }

    private fun verifyPassword(listUser: List<UserData>,
                               password: String): UserData?{
        if (!listUser.isEmpty()){
            for (i in listUser.indices){
                val us = listUser[i]
                if (us.password == HashUtils.sha512(password)){
                    return listUser[i]
                }
            }
        }
        return null
    }
}