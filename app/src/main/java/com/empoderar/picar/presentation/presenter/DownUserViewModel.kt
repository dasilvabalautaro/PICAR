package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import com.empoderar.picar.domain.interactor.DownUsersUseCase
import com.empoderar.picar.domain.data.User
import com.empoderar.picar.domain.interactor.UseCase
import com.empoderar.picar.presentation.data.UserView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import javax.inject.Inject

class DownUserViewModel @Inject constructor(private val downUsersUseCase:
                                            DownUsersUseCase): BaseViewModel() {
    var result: MutableLiveData<List<UserView>> = MutableLiveData()

    fun loadUsers() = downUsersUseCase(UseCase.None()){
        it.either(::handleFailure, ::handleUserList)
    }

    private fun handleUserList(users: List<User>) {
        this.result.value = users.map { UserView(it.id, it.unit,
                it.role, it.name, it.phone, it.address) }
    }

}