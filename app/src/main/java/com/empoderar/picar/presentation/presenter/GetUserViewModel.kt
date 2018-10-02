package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import com.empoderar.picar.domain.interactor.GetUserUseCase
import com.empoderar.picar.model.persistent.database.data.UserData
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetUserViewModel @Inject constructor(private val getUserUseCase:
                                           GetUserUseCase): BaseViewModel() {
    var result: MutableLiveData<UserData> = MutableLiveData()

    var list: List<String>? = null

    fun getUser() = getUserUseCase(list!!){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: UserData){
        result.value = value

    }

}