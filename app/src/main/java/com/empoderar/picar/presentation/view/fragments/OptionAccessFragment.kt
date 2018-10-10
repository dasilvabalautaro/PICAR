package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import kotlinx.android.synthetic.main.view_options_access.*
import javax.inject.Inject

class OptionAccessFragment: BaseFragment() {
    @Inject
    lateinit var navigator: Navigator


    override fun layoutId() = R.layout.view_options_access

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ib_forms.setOnClickListener{}
    }

    override fun renderFailure(message: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}