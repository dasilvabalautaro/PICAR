package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.interactor.GetFormsUseCase
import com.empoderar.picar.domain.interactor.UseCase
import com.empoderar.picar.presentation.data.FormView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetFormsViewModel @Inject constructor(private val getFormsUseCase:
                                            GetFormsUseCase): BaseViewModel() {

    var result: MutableLiveData<List<FormView>> = MutableLiveData()

    fun loadForms() = getFormsUseCase(UseCase.None()){
        it.either(::handleFailure, ::handleFormList)
    }

    private fun handleFormList(forms: List<Form>) {
        this.result.value = forms.map { FormView(it.id, it.project, it.frmId,
                it.frmNro, it.title, it.dateEvaluation, it.state, it.observation,
                it.userMobile, it.latitude, it.longitude, it.dateCreation, it.dateModification) }
    }

}