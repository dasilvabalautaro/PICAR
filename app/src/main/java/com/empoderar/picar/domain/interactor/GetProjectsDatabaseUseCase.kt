package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Project
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.interfaces.ProjectDataDao
import javax.inject.Inject

class GetProjectsDatabaseUseCase @Inject constructor(private val projectDataDao:
                                             ProjectDataDao):
        UseCase<List<Project>, UseCase.None>() {


    override suspend fun run(params: None): Either<Failure, List<Project>> {
        return try {
            val listData = projectDataDao.getAll()
            val list = listData.map { Project(it.id, it.unity,
                    it.codeProject, it.nameProject, it.latitude, it.longitude,
                    it.state, it.datePresentation, it.dateInitAgreement, it.dateEndAgreement) }
            when(list.isNotEmpty()){
                true -> Either.Right(list)
                false -> Either.Left(Failure.DatabaseError())
            }

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }
    }

}