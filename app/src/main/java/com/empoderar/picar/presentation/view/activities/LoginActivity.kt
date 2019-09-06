package com.empoderar.picar.presentation.view.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.empoderar.picar.model.persistent.caching.Variables
import com.empoderar.picar.presentation.plataform.BaseActivity
import com.empoderar.picar.presentation.view.fragments.LoginFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import com.empoderar.picar.model.exception.NoNetworkException

class LoginActivity: BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context,
                LoginActivity::class.java)
    }

    override fun fragment() = LoginFragment()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        picarWebService.getPublicEvents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Variables.isServerUp = true
                    Toast.makeText(this,
                            "Successful Request", Toast.LENGTH_SHORT).show()
                }, { throwable ->

                    if (throwable is NoNetworkException) {

                        Variables.isServerUp = false
                        Toast.makeText(this,
                                "No Network Connection", Toast.LENGTH_SHORT).show()
                    } else {
                        Variables.isServerUp = false
                        Toast.makeText(this, "Connection Some Other Error", Toast.LENGTH_SHORT)
                                .show()
                    }
                })


    }

}