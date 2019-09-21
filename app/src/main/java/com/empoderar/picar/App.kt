package com.empoderar.picar

import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.res.Configuration
import com.empoderar.picar.di.ApplicationComponent
import com.empoderar.picar.di.ApplicationModule
import com.empoderar.picar.di.DaggerApplicationComponent
import com.empoderar.picar.model.observer.AppLifecycleObserver
import com.empoderar.picar.presentation.tools.LocaleUtils
import com.squareup.leakcanary.LeakCanary
import java.util.*
import javax.inject.Inject

//Init Application

class App: Application() {
//    Component of Inject
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    @Inject
    lateinit var appLifecycleObserver: AppLifecycleObserver
    @Inject
    lateinit var localeUtils: LocaleUtils

//    Create inject
    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        this.initializeLeakDetection()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
        localeUtils.setLocale(Locale("es"))
        localeUtils.updateConfiguration(this,
                baseContext.resources.configuration)
    }

    private fun injectMembers() = appComponent.inject(this)

//    Verify leak memory in debug
    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        localeUtils.updateConfiguration(this, newConfig!!)
    }
}