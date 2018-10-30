package com.empoderar.picar.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.empoderar.picar.presentation.presenter.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

// The key binds provider and ViewModel.

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory:
                                               ViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(InsertUsersViewModel::class)
    abstract fun bindsInsertUsersViewModel(insertUsersViewModel: InsertUsersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DownUserViewModel::class)
    abstract fun bindsDownUserViewModel(downUserViewModel: DownUserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetFormsViewModel::class)
    abstract fun bindsGetFormsViewModel(getFormsViewModel: GetFormsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetProjectsViewModel::class)
    abstract fun bindsGetProjectsViewModel(getProjectsViewModel: GetProjectsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetUnitsViewModel::class)
    abstract fun bindsGetUnitsViewModel(getUnitsViewModel: GetUnitsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetMunicipalitiesViewModel::class)
    abstract fun bindsGetMunicipalitiesViewModel(getMunicipalitiesViewModel:
                                                     GetMunicipalitiesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetUserViewModel::class)
    abstract fun bindsGetUserViewModel(getUserViewModel:
                                           GetUserViewModel): ViewModel

}