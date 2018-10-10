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
        it.either(::handleFailure, ::handleUserList)
    }

    private fun handleUserList(forms: List<Form>) {
        this.result.value = forms.map { FormView(it.id, it.unit, it.user,
                it.dateForm, it.lat, it.lon, it.variable1, it.variable2,
                it.variable3, it.variable4, it.comment1, it.comment2, it.comment3,
                it.updateDate, it.updateUser) }
    }

}