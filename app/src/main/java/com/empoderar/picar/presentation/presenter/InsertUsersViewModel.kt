package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import com.empoderar.picar.domain.interactor.InsertUsersUseCase
import com.empoderar.picar.presentation.data.UserView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject


class InsertUsersViewModel @Inject constructor(private val insertUsersUseCase:
                                               InsertUsersUseCase): BaseViewModel() {
    var result: MutableLiveData<Boolean> = MutableLiveData()

    var list: List<UserView>? = null

    fun insertUsers() = insertUsersUseCase(list!!){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: Boolean){
        result.value = value

    }

}