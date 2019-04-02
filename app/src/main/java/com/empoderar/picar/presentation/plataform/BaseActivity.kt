package com.empoderar.picar.presentation.plataform

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.empoderar.picar.App
import com.empoderar.picar.R
import com.empoderar.picar.presentation.extension.inTransaction
import com.empoderar.picar.R.id
import com.empoderar.picar.R.layout
import com.empoderar.picar.di.ApplicationComponent
import com.empoderar.picar.presentation.permission.EnablePermissions
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_task.*
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity() {

    val appComponent: ApplicationComponent by
    lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as App).appComponent
    }


    @Inject
    lateinit var networkHandler: NetworkHandler
    @Inject
    lateinit var enablePermissions: EnablePermissions
    internal var disposable: CompositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_task)
        navList.visibility = View.INVISIBLE
        addFragment(savedInstanceState)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
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

    @SuppressLint("PrivateResource")
    fun addFragment(newFragment: BaseFragment) {
        /*supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in,
                        R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.fragmentContainer, newFragment, newFragment.javaClass.simpleName)
                .commit()*/

        supportFragmentManager.inTransaction { add(
                id.fragmentContainer, newFragment) }
    }
}