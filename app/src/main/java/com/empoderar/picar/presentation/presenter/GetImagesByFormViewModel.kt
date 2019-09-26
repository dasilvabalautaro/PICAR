package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.Image
import com.empoderar.picar.domain.interactor.GetImagesByFormUseCase
import com.empoderar.picar.presentation.data.ImageView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetImagesByFormViewModel @Inject constructor(private val getImagesByFormUseCase:
                                                   GetImagesByFormUseCase):
        BaseViewModel() {
    var result: MutableLiveData<List<ImageView>> = MutableLiveData()
    var idForm: Int? = null

    fun loadImages() = getImagesByFormUseCase(idForm!!){
        it.either(::handleFailure, ::handleFormList)
    }

    private fun handleFormList(images: List<Image>) {
        this.result.value = images.map { ImageView(it.id, it.form,
                it.project, it.base64, it.latitude, it.longitude, it.date) }
    }

}