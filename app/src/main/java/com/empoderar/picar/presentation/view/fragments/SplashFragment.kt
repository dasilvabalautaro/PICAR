package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.os.Handler
import com.empoderar.picar.R
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import javax.inject.Inject


class SplashFragment: BaseFragment() {
    @Inject
    lateinit var navigator: Navigator

    override fun layoutId() = R.layout.view_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        Handler().postDelayed({
            navigator.showOptions(activity!!)
        }, 3000)
    }

    override fun renderFailure(message: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}