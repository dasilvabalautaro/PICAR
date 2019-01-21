package com.empoderar.picar.presentation.view.activities

import android.content.Context
import android.content.Intent
import com.empoderar.picar.presentation.plataform.BaseActivity
import com.empoderar.picar.presentation.view.fragments.LoginFragment

class LoginActivity: BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context,
                LoginActivity::class.java)
    }

    override fun fragment() = LoginFragment()

}