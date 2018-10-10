package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.Project
import com.empoderar.picar.domain.interactor.GetProjectsUseCase
import com.empoderar.picar.domain.interactor.UseCase
import com.empoderar.picar.presentation.data.ProjectView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetProjectsViewModel @Inject constructor(private val getProjectsUseCase:
                                               GetProjectsUseCase): BaseViewModel() {
    var result: MutableLiveData<List<ProjectView>> = MutableLiveData()

    fun loadProjects() = getProjectsUseCase(UseCase.None()){
        it.either(::handleFailure, ::handleUserList)
    }

    private fun handleUserList(projects: List<Project>) {
        this.result.value = projects.map { ProjectView(it.id, it.unit,
                it.municipality, it.type, it.code, it.name, it.lat, it.lon,
                it.mount, it.counterpart, it.notFinance, it.other, it.total) }
    }

}