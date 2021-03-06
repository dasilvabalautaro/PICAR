package com.empoderar.picar.presentation.plataform

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.empoderar.picar.App
import com.empoderar.picar.R
import com.empoderar.picar.presentation.extension.inTransaction
import com.empoderar.picar.R.id
import com.empoderar.picar.R.layout
import com.empoderar.picar.di.ApplicationComponent
import com.empoderar.picar.model.persistent.network.interfaces.PicarWebService
import com.empoderar.picar.presentation.permission.EnablePermissions
import com.empoderar.picar.presentation.view.activities.MenuActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_task.*
import org.jetbrains.anko.toast
import javax.inject.Inject
import kotlin.system.exitProcess

abstract class BaseActivity: AppCompatActivity() {

    val appComponent: ApplicationComponent by
    lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as App).appComponent
    }


    @Inject
    lateinit var networkHandler: NetworkHandler
    @Inject
    lateinit var picarWebService: PicarWebService
    @Inject
    lateinit var enablePermissions: EnablePermissions
    internal var disposable: CompositeDisposable = CompositeDisposable()
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_task)
        navList.visibility = View.INVISIBLE
        addFragment(savedInstanceState)

    }


    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(
                id.fragmentContainer) as BaseFragment).onBackPressed()

        if (this is MenuActivity){
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                android.os.Process.killProcess(android.os.Process.myPid())
                exitProcess(1)
            }

            this.doubleBackToExitPressedOnce = true
            toast("Please click BACK again to exit")

            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false },
                    2000)

        } else{
            super.onBackPressed()
        }
    }


    private fun addFragment(savedInstanceState: Bundle?) =
            savedInstanceState ?: supportFragmentManager.inTransaction { add(
                    id.fragmentContainer, fragment()) }

    abstract fun fragment(): BaseFragment

    @SuppressLint("PrivateResource")
    fun addFragment(newFragment: BaseFragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in,
                        R.anim.design_bottom_sheet_slide_out)
                .replace(id.fragmentContainer, newFragment, newFragment.javaClass.simpleName)
                .commit()
                //.addToBackStack(null)
        /*supportFragmentManager.inTransaction { add(
                id.fragmentContainer, newFragment) }*/
    }
}