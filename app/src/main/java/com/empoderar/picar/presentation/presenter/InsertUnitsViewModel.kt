package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.Unity
import com.empoderar.picar.domain.interactor.InsertUnitsUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class InsertUnitsViewModel @Inject constructor(private val insertUnitsUseCase:
                                               InsertUnitsUseCase):
        BaseViewModel() {
    var result: MutableLiveData<Boolean> = MutableLiveData()

    var list: List<Unity>? = null

    fun insertUnits() = insertUnitsUseCase(list!!){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: Boolean){
        result.value = value

    }

}