package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.interactor.InsertOneFormUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class InsertOneFormViewModel @Inject constructor(private val insertOneFormUseCase:
                                                 InsertOneFormUseCase):
        BaseViewModel() {
    var result: MutableLiveData<Long> = MutableLiveData()

    var form: Form? = null

    fun insertForm() = insertOneFormUseCase(form!!){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: Long){
        result.value = value

    }

}