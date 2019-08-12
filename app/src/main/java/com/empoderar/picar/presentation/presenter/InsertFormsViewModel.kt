package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.interactor.InsertFormsUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class InsertFormsViewModel @Inject constructor(private val insertFormsUseCase:
                                               InsertFormsUseCase):
        BaseViewModel() {
    var result: MutableLiveData<Boolean> = MutableLiveData()

    var list: List<Form>? = null

    fun insertForms() = insertFormsUseCase(list!!){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: Boolean){
        result.value = value

    }

}