package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.Unity
import com.empoderar.picar.domain.interactor.GetUnitsDatabaseUseCase
import com.empoderar.picar.domain.interactor.UseCase
import com.empoderar.picar.presentation.data.UnityView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetUnitsDatabaseViewModel @Inject constructor(private val getUnitsUseCase:
                                            GetUnitsDatabaseUseCase):
        BaseViewModel() {
    var result: MutableLiveData<List<Unity>> = MutableLiveData()

    fun loadUnities() = getUnitsUseCase(UseCase.None()){
        it.either(::handleFailure, ::handleUserList)
    }

    private fun handleUserList(unities: List<Unity>) {
        this.result.value = unities

        /*unities.map { UnityView(it.id, it.name,
                it.phone, it.address) }*/
    }

}