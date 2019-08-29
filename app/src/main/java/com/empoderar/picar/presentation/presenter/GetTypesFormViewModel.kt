package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.TypeForm
import com.empoderar.picar.domain.interactor.GetTypesFormUseCase
import com.empoderar.picar.domain.interactor.UseCase
import com.empoderar.picar.presentation.data.TypeFormView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetTypesFormViewModel @Inject constructor(private val getTypesFormUseCase:
                                                GetTypesFormUseCase):
        BaseViewModel() {
    var result: MutableLiveData<List<TypeFormView>> = MutableLiveData()

    fun loadTypesForm() = getTypesFormUseCase(UseCase.None()){
        it.either(::handleFailure, ::handleFormList)
    }

    private fun handleFormList(types: List<TypeForm>) {
        this.result.value = types.map { TypeFormView(it.frmId, it.idEtapa,
                it.titulo, it.maximo, it.ponderacion, it.observation,
                it.maxi, it.valor, it.logico, it.fecha, it.texto) }
    }

}