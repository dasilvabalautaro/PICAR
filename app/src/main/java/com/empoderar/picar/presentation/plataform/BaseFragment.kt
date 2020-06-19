package com.empoderar.picar.presentation.plataform

import androidx.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.empoderar.picar.App
import com.empoderar.picar.R
import com.empoderar.picar.di.ApplicationComponent
import com.empoderar.picar.domain.data.Unity
import com.empoderar.picar.model.exception.Failure
import com.empoderar.picar.presentation.data.*
import com.empoderar.picar.presentation.extension.viewContainer
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

abstract class BaseFragment: Fragment() {
    companion object Factory {
        //val mapImage: LinkedHashMap<String, ProxyBitmap> = LinkedHashMap()
        var listMunicipality : List<MunicipalityView>? = null
        var userMain: UserView? = null
        var listUnity: List<Unity>? = null
        var listProject: List<ProjectView>? = null
        var proyectView: ProjectView? = null
        var listForm: List<FormView>? = null
        var currentProject: Int = 0
        var oldProject: Int = 0
        var isNewProject = false

    }

    abstract fun layoutId(): Int
    internal var disposable: CompositeDisposable = CompositeDisposable()
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as App).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
            inflater.inflate(layoutId(), container, false)

    open fun onBackPressed() {}

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    internal fun showProgress() = progressStatus(View.VISIBLE)

    internal fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
            with(activity) { if (this is BaseActivity)
                this.progress.visibility = viewStatus }

    abstract fun renderFailure(@StringRes message: Int)

    internal fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            is Failure.DatabaseError -> renderFailure(R.string.failure_database_error)
        }
    }
    internal fun notify(@StringRes message: Int) =
            Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    internal fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}