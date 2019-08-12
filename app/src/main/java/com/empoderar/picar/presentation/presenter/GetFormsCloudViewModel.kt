package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import android.webkit.URLUtil
import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.interactor.GetFormsCloudUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetFormsCloudViewModel @Inject constructor(private val getFormsCloudUseCase:
                                                 GetFormsCloudUseCase):
        BaseViewModel() {
    var result: MutableLiveData<List<Form>> = MutableLiveData()

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

    fun requestForms() = getFormsCloudUseCase(this.params){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(list: List<Form>){

        result.value = list

    }

}