package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Project
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.database.data.ProjectData
import com.empoderar.picar.model.persistent.database.interfaces.ProjectDataDao
import javax.inject.Inject

class InsertProjectsUseCase @Inject constructor(private val projectDataDao:
                                                ProjectDataDao):
        UseCase<Boolean, List<Project>>() {
    override suspend fun run(params: List<Project>): Either<Failure, Boolean> {
        return try {
            val list = transformList(params)

            when (list.isNotEmpty()){
                true -> {
                    projectDataDao.insertProjects(list)
                    Either.Right(true)
                }
                false -> Either.Left(Failure.DatabaseError())
            }

        }catch (exception: Throwable){
            Either.Left(Failure.DatabaseError())
        }

    }

    private fun transformList(list: List<Project>): List<ProjectData>{
        val projects = ArrayList<ProjectData>()

        if (list.isNotEmpty()){
            for (i in list.indices){
                val project = ProjectData(list[i].id, list[i].unity, list[i].codeProject,
                        list[i].nameProject, list[i].latitude, list[i].longitude, list[i].state,
                        list[i].datePresentation, list[i].dateInitAgreement, list[i].dateEndAgreement)
                projects.add(project)
            }
        }
        return projects.toList()
    }

}