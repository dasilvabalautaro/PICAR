package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.Project
import com.empoderar.picar.domain.interactor.InsertProjectsUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class InsertProjectsViewModel @Inject constructor(private val insertProjectsUseCase:
                                                  InsertProjectsUseCase):
        BaseViewModel() {
    var result: MutableLiveData<Boolean> = MutableLiveData()

    var list: List<Project>? = null

    fun insertProjects() = insertProjectsUseCase(list!!){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: Boolean){
        result.value = value

    }
}