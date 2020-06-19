package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.Project
import com.empoderar.picar.domain.interactor.GetProjectsDatabaseUseCase
import com.empoderar.picar.domain.interactor.UseCase
import com.empoderar.picar.presentation.data.ProjectView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetProjectsDatabaseViewModel @Inject constructor(private val getProjectsUseCase:
                                               GetProjectsDatabaseUseCase): BaseViewModel() {
    var result: MutableLiveData<List<ProjectView>> = MutableLiveData()

    fun loadProjects() = getProjectsUseCase(UseCase.None()){
        it.either(::handleFailure, ::handleUserList)
    }

    private fun handleUserList(projects: List<Project>) {
        this.result.value = projects.map { ProjectView(it.id, it.unity,
                it.codeProject, it.nameProject, it.latitude, it.longitude, it.state, it.datePresentation,
                it.dateInitAgreement, it.dateEndAgreement) }
    }

}