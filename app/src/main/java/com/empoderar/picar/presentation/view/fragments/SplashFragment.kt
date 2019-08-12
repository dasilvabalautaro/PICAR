package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.os.Handler
import com.empoderar.picar.R
import com.empoderar.picar.presentation.data.UserView
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.DownUserViewModel
import com.empoderar.picar.presentation.presenter.InsertUsersViewModel
import com.empoderar.picar.presentation.view.activities.SplashActivity
import javax.inject.Inject


class SplashFragment: BaseFragment() {
    @Inject
    lateinit var navigator: Navigator
    private lateinit var downUserViewModel: DownUserViewModel
    private lateinit var insertUsersViewModel: InsertUsersViewModel

    override fun layoutId() = R.layout.view_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        downUserViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleDownUsers)
            failure(failure, ::handleFailure)
        }
        insertUsersViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleSaveUsers)
            failure(failure, ::handleFailure)
        }

        Handler().postDelayed({
            navigator.showOptions(activity!!)
            (activity!! as SplashActivity).finish()
        }, 3000)
    }

    private fun handleDownUsers(list: List<UserView>?){
        if (list != null && !list.isEmpty()){
            insertUsersViewModel.list = list
            insertUsersViewModel.insertUsers()
        }
    }

    private fun handleSaveUsers(value: Boolean?){
        if (value != null && value){
            context!!.toast(getString(R.string.msg_users_saved))
        }
    }

    private fun throwDownload(){
        showProgress()
        downUserViewModel.loadUsers()
    }

    override fun renderFailure(message: Int) {
        hideProgress()
        notify(message)
    }
}