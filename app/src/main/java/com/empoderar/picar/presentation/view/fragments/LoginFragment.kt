package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.presentation.data.UserView
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.GetUserViewModel
import com.empoderar.picar.presentation.tools.Validate
import kotlinx.android.synthetic.main.view_login.*
import javax.inject.Inject

class LoginFragment: BaseFragment() {
    @Inject
    lateinit var navigator: Navigator

    private lateinit var getUserViewModel: GetUserViewModel

    override fun layoutId() = R.layout.view_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        getUserViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetUser)
            failure(failure, ::handleFailure)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ib_access.setOnClickListener { checkUser() }
    }

    private fun checkUser(){
        if (validatedInput(et_user.text.toString(),
                        et_password.text.toString())){
            val list = listOf(et_user.text.toString(),
                    et_password.text.toString())
            getUserViewModel.list = list
            getUserViewModel.getUser()
        }else{
            notify(R.string.failure_input)
        }
        navigator.showMenu(activity!!)
    }
    private fun handleGetUser(user: UserView?){
        if (user != null){
            userMain = user
            navigator.showMenu(activity!!)
        }

    }

    override fun renderFailure(message: Int) {
        notify(message)
    }

    private fun validatedInput(name: String, password: String): Boolean{
        return name.length > 5 && password.length > 5
    }
}