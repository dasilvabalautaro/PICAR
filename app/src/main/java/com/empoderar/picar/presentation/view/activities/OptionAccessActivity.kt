package com.empoderar.picar.presentation.view.activities

import android.content.Context
import android.content.Intent
import com.empoderar.picar.presentation.plataform.BaseActivity
import com.empoderar.picar.presentation.view.fragments.OptionAccessFragment

class OptionAccessActivity: BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context,
                OptionAccessActivity::class.java)
    }

    override fun fragment() = OptionAccessFragment()



}