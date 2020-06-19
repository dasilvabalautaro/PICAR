package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import com.empoderar.picar.domain.data.User
import com.empoderar.picar.domain.interactor.GetUserUseCase
import com.empoderar.picar.presentation.data.UserView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class GetUserViewModel @Inject constructor(private val getUserUseCase:
                                           GetUserUseCase): BaseViewModel() {
    var result: MutableLiveData<UserView> = MutableLiveData()

    var list: List<String>? = null

    fun getUser() = getUserUseCase(list!!){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: User){
        result.value = UserView(value.id, value.unit, value.role, value.name,
                value.phone, value.address)

    }

}