package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.ContentForm
import com.empoderar.picar.domain.interactor.GetContentFormsUseCase
import com.empoderar.picar.presentation.data.ContentFormView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetContentFormsViewModel @Inject constructor(private val getContentFormsUseCase:
                                                   GetContentFormsUseCase):
        BaseViewModel() {

    var result: MutableLiveData<List<ContentFormView>> = MutableLiveData()

    var frmId: String? = null

    fun loadContents() = getContentFormsUseCase(frmId!!){
        it.either(::handleFailure, ::handleFormList)
    }

    private fun handleFormList(forms: List<ContentForm>) {
        this.result.value = forms.map { ContentFormView(it.id, it.frmId,
                it.varTipo, it.varCodigo, it.description, it.valor,
                it.logico, it.fecha, it.texto) }
    }

}