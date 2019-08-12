package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import android.webkit.URLUtil
import com.empoderar.picar.domain.data.Project
import com.empoderar.picar.domain.interactor.GetProjectsCloudUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetProjectsCloudViewModel @Inject constructor(private val getProjectsCloudUseCase:
                                                    GetProjectsCloudUseCase):
        BaseViewModel() {
    var result: MutableLiveData<List<Project>> = MutableLiveData()

    lateinit var url: String
    lateinit var token: String
    private lateinit var params: List<String>

    fun verifyInput(): Boolean{
        if (URLUtil.isValidUrl(this.url) &&
                this.token.isNotEmpty()){
            this.params = listOf(this.token, this.url)
            return true

        }
        return false
    }

    fun requestProjects() = getProjectsCloudUseCase(this.params){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(list: List<Project>){

        result.value = list

    }

}