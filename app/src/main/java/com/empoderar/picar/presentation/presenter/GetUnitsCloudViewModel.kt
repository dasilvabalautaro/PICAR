package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import android.webkit.URLUtil
import com.empoderar.picar.domain.data.Permission
import com.empoderar.picar.domain.data.Unity
import com.empoderar.picar.domain.interactor.GetUnitsCloudUseCase
import com.empoderar.picar.domain.interactor.InsertUnitsUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetUnitsCloudViewModel @Inject constructor(private val getUnitsCloudUseCase:
                                                 GetUnitsCloudUseCase):
        BaseViewModel() {
    var result: MutableLiveData<List<Unity>> = MutableLiveData()
    lateinit var url: String
    lateinit var token: String
    private lateinit var params: List<String>

    fun verifyInput(): Boolean{
        if (URLUtil.isValidUrl(this.url) &&
                this.token.isNotEmpty()){
            this.params = listOf(this.token, this.url)
            return true

        }
        return false
    }

    fun requestUnits() = getUnitsCloudUseCase(this.params){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(list: List<Unity>){

        result.value = list

    }


}