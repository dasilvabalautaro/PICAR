package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.Image
import com.empoderar.picar.domain.interactor.InsertImagesUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class InsertImagesViewModel @Inject constructor(private val insertImagesUseCase:
                                                InsertImagesUseCase):
        BaseViewModel() {
    var result: MutableLiveData<Boolean> = MutableLiveData()

    var list: List<Image>? = null

    fun insertImages() = insertImagesUseCase(list!!){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: Boolean){
        result.value = value

    }

}