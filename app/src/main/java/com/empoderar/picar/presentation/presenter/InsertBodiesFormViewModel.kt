package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.BodyForm
import com.empoderar.picar.domain.interactor.InsertBodiesFormUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class InsertBodiesFormViewModel @Inject constructor(private val insertBodiesFormUseCase:
                                                    InsertBodiesFormUseCase):
        BaseViewModel() {
    var result: MutableLiveData<Boolean> = MutableLiveData()

    var list: List<BodyForm> ? = null

    fun insertBodiesForm() = insertBodiesFormUseCase(list!!){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: Boolean){
        result.value = value

    }

}