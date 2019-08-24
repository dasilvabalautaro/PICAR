package com.empoderar.picar.di

import android.content.Context
import com.empoderar.picar.App
import com.empoderar.picar.di.viewmodel.ViewModelModule
import com.empoderar.picar.model.persistent.network.LocationListener
import com.empoderar.picar.presentation.view.activities.MenuActivity
import com.empoderar.picar.presentation.view.activities.RouteActivity
import com.empoderar.picar.presentation.view.fragments.*
import dagger.Component
import javax.inject.Singleton

//Interface for Inject dependencies, all the Classes injected. One only Instance.
@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(app: App)
    fun context(): Context
    fun inject(routeActivity: RouteActivity)
    fun inject(menuActivity: MenuActivity)
    fun inject(splashFragment: SplashFragment)
    fun inject(projectsFragment: ProjectsFragment)
    fun inject(formsFragment: FormsFragment)
    fun inject(listingsFragment: ListingsFragment)
    fun inject(optionAccessFragment: OptionAccessFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(locationListener: LocationListener)
    fun inject(mapFragment: MapFragment)
    fun inject(newFormFragment: NewFormFragment)
    fun inject(mapProjectsFragment: MapProjectsFragment)
}