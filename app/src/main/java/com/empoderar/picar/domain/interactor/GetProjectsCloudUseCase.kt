package com.empoderar.picar.domain.interactor

import com.empoderar.picar.domain.data.Project
import com.empoderar.picar.domain.functional.Either
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.model.persistent.network.apply.LoadRequestProject
import javax.inject.Inject

class GetProjectsCloudUseCase @Inject constructor(private val loadRequestProject:
                                                  LoadRequestProject):
        UseCase<List<Project>, List<String>>() {

    override suspend fun run(params: List<String>): Either<Failure, List<Project>> {
        val token = params[0]
        val url = params[1]
        return loadRequestProject.getResult(token, url)
    }
}