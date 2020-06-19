package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import com.empoderar.picar.domain.interactor.UpdateUploadAllFormsUseCase
import com.empoderar.picar.domain.interactor.UseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class UpdateUploadAllFormsViewModel @Inject constructor(private val updateUploadAllFormsUseCase:
                                                        UpdateUploadAllFormsUseCase):
        BaseViewModel() {

    var result: MutableLiveData<Boolean> = MutableLiveData()


    fun updateUploadForms() = updateUploadAllFormsUseCase(UseCase.None()){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: Boolean){
        result.value = value

    }

}