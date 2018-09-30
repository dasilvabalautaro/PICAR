package com.empoderar.picar.di

import com.empoderar.picar.App
import com.empoderar.picar.di.viewmodel.ViewModelModule
import com.empoderar.picar.presentation.view.activities.RouteActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(app: App)
    fun inject(routeActivity: RouteActivity)
}