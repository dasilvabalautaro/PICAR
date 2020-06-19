package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import android.webkit.URLUtil
import com.empoderar.picar.domain.data.ContentForm
import com.empoderar.picar.domain.interactor.GetContentFormsCloudUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetContentFormsCloudViewModel @Inject constructor(private val getContentFormsCloudUseCase:
                                                        GetContentFormsCloudUseCase):
        BaseViewModel() {

    var result: MutableLiveData<List<ContentForm>> = MutableLiveData()

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

    fun requestContentForms() = getContentFormsCloudUseCase(this.params){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(list: List<ContentForm>){

        result.value = list

    }

}