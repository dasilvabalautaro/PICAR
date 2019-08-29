package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import android.webkit.URLUtil
import com.empoderar.picar.domain.data.TypeForm
import com.empoderar.picar.domain.interactor.GetTypesFormCloudUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetTypesFormCloudViewModel @Inject constructor(private val getTypesFormCloudUseCase:
                                                     GetTypesFormCloudUseCase):
        BaseViewModel(){
    var result: MutableLiveData<List<TypeForm>> = MutableLiveData()

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

    fun requestTypesForm() = getTypesFormCloudUseCase(this.params){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(list: List<TypeForm>){

        result.value = list

    }

}