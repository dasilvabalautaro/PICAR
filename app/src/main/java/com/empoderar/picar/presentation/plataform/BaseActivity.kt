package com.empoderar.picar.presentation.plataform

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.empoderar.picar.presentation.extension.inTransaction
import com.empoderar.picar.R.id
import com.empoderar.picar.R.layout
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_task)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        addFragment(savedInstanceState)

    }


    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(
                id.fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }


    private fun addFragment(savedInstanceState: Bundle?) =
            savedInstanceState ?: supportFragmentManager.inTransaction { add(
                    id.fragmentContainer, fragment()) }

    abstract fun fragment(): BaseFragment

}