package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import android.webkit.URLUtil
import com.empoderar.picar.domain.data.Message
import com.empoderar.picar.domain.interactor.UploadFormUseCase
import com.empoderar.picar.model.persistent.network.entity.FormEntity
import com.empoderar.picar.model.persistent.network.entity.MessageEntity
import com.empoderar.picar.presentation.data.FormView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import com.google.gson.Gson
import javax.inject.Inject

class UploadFormsViewModel @Inject constructor(private val uploadFormUseCase:
                                               UploadFormUseCase):
        BaseViewModel() {
    var result: MutableLiveData<Message> = MutableLiveData()

    lateinit var url: String
    lateinit var token: String
    lateinit var forms: List<FormView>
    private lateinit var params: List<String>

    fun buildParams(): Boolean{
        if (URLUtil.isValidUrl(this.url) &&
                this.token.isNotEmpty()){
            val list = forms.map { FormEntity(it.id,
                    it.project, it.frmId, it.frmNro, it.title, it.dateEvaluation,
                    it.state, it.observation, it.userMobile,
                    it.latitude, it.longitude, it.dateCreation, it.dateModification) }
            val gson = Gson()
            val jsonString = gson.toJson(list)
            println(jsonString)
            if (jsonString.isNotEmpty()){
                this.params = listOf(this.token, this.url, jsonString)
                return true
            }

        }
        return false

    }

    fun requestPost() = uploadFormUseCase(this.params){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: Message){
        result.value = value

    }

}