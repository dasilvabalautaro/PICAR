package com.empoderar.picar.di

import com.empoderar.picar.App
import com.empoderar.picar.di.viewmodel.ViewModelModule
import com.empoderar.picar.presentation.view.activities.MenuActivity
import com.empoderar.picar.presentation.view.activities.RouteActivity
import com.empoderar.picar.presentation.view.fragments.ProjectsFragment
import com.empoderar.picar.presentation.view.fragments.SplashFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(app: App)
    fun inject(routeActivity: RouteActivity)
    fun inject(menuActivity: MenuActivity)
    fun inject(splashFragment: SplashFragment)
    fun inject(projectsFragment: ProjectsFragment)
}