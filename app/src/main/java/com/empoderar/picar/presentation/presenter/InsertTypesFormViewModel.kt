package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.TypeForm
import com.empoderar.picar.domain.interactor.InsertTypesFormUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class InsertTypesFormViewModel @Inject constructor(private val insertTypesFormUseCase:
                                                   InsertTypesFormUseCase):
        BaseViewModel() {
    var result: MutableLiveData<Boolean> = MutableLiveData()

    var list: List<TypeForm>? = null

    fun insertTypesForm() = insertTypesFormUseCase(list!!){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: Boolean){
        result.value = value

    }

}