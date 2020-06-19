package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.ContentForm
import com.empoderar.picar.domain.interactor.InsertContentFormsUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class InsertContentFormsViewModel @Inject constructor(private val insertContentFormsUseCase:
                                                      InsertContentFormsUseCase):
        BaseViewModel() {

    var result: MutableLiveData<Boolean> = MutableLiveData()

    var list: List<ContentForm>? = null

    fun insertContents() = insertContentFormsUseCase(list!!){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: Boolean){
        result.value = value

    }

}