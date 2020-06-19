package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.BodyForm
import com.empoderar.picar.domain.interactor.GetBodyFormUseCase
import com.empoderar.picar.presentation.data.BodyFormView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetBodyFormViewModel @Inject constructor(private val getBodyFormUseCase:
                                               GetBodyFormUseCase):
        BaseViewModel() {
    var result: MutableLiveData<List<BodyFormView>> = MutableLiveData()

    var formId: Int? = null

    fun loadBodyForm() = getBodyFormUseCase(formId!!){
        it.either(::handleFailure, ::handleFormList)
    }

    private fun handleFormList(forms: List<BodyForm>) {
        this.result.value = forms.map { BodyFormView(it.id, it.formId,
                it.idProject, it.code, it.description, it.value, it.satisfy,
                it.date, it.comment) }
    }

}