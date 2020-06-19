package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import com.empoderar.picar.domain.interactor.UpdateUploadFormUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class UpdateUploadFormsViewModel @Inject constructor(private val updateUploadFormUseCase:
                                                     UpdateUploadFormUseCase):
        BaseViewModel() {
    var result: MutableLiveData<Boolean> = MutableLiveData()

    var idForm: Int? = null
    var value: Int? = null
    private lateinit var params: List<Int>

    fun buildParams(){
        this.params = listOf(this.idForm!!, this.value!!)
    }

    fun updateUploadForms() = updateUploadFormUseCase(params){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: Boolean){
        result.value = value

    }

}