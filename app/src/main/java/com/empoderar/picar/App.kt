package com.empoderar.picar

import android.app.Application
import com.empoderar.picar.di.ApplicationComponent
import com.empoderar.picar.di.ApplicationModule
import com.empoderar.picar.di.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary

class App: Application() {
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        this.initializeLeakDetection()
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}