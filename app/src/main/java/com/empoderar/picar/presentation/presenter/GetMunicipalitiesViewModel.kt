package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.Municipality
import com.empoderar.picar.domain.interactor.GetMunicipalitiesUseCase
import com.empoderar.picar.domain.interactor.UseCase
import com.empoderar.picar.presentation.data.MunicipalityView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetMunicipalitiesViewModel @Inject constructor(private val getMunicipalitiesUseCase:
                                                     GetMunicipalitiesUseCase): BaseViewModel() {

    var result: MutableLiveData<List<MunicipalityView>> = MutableLiveData()

    fun loadMunicipalities() = getMunicipalitiesUseCase(UseCase.None()){
        it.either(::handleFailure, ::handleMunicipalityList)
    }

    private fun handleMunicipalityList(municipalities: List<Municipality>) {
        this.result.value = municipalities.map { MunicipalityView(it.id, it.unit, it.name) }
    }

}