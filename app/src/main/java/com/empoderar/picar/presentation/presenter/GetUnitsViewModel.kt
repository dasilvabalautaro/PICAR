package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.Unity
import com.empoderar.picar.domain.interactor.GetUnitsUseCase
import com.empoderar.picar.domain.interactor.UseCase
import com.empoderar.picar.presentation.data.UnityView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetUnitsViewModel @Inject constructor(private val getUnitsUseCase:
                                            GetUnitsUseCase):
        BaseViewModel() {
    var result: MutableLiveData<List<UnityView>> = MutableLiveData()

    fun loadUnities() = getUnitsUseCase(UseCase.None()){
        it.either(::handleFailure, ::handleUserList)
    }

    private fun handleUserList(unities: List<Unity>) {
        this.result.value = unities.map { UnityView(it.id, it.name,
                it.phone, it.address) }
    }

}