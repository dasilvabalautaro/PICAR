package com.empoderar.picar

import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import com.empoderar.picar.di.ApplicationComponent
import com.empoderar.picar.di.ApplicationModule
import com.empoderar.picar.di.DaggerApplicationComponent
import com.empoderar.picar.model.observer.AppLifecycleObserver
import com.squareup.leakcanary.LeakCanary
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

//    Create inject
    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        this.initializeLeakDetection()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
    }

    private fun injectMembers() = appComponent.inject(this)

//    Verify leak memory in debug
    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}