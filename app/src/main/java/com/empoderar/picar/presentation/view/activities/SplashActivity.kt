package com.empoderar.picar.presentation.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.empoderar.picar.presentation.plataform.BaseActivity
import com.empoderar.picar.presentation.view.fragments.SplashFragment

class SplashActivity: BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context,
                SplashActivity::class.java)
    }

    override fun fragment() = SplashFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
    }
}