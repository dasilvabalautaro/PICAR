package com.empoderar.picar.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.empoderar.picar.presentation.presenter.GetProjectsCloudViewModel
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
    @ViewModelKey(GetProjectsDatabaseViewModel::class)
    abstract fun bindsGetProjectsViewModel(getProjectsViewModel: GetProjectsDatabaseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetUnitsDatabaseViewModel::class)
    abstract fun bindsGetUnitsViewModel(getUnitsViewModel: GetUnitsDatabaseViewModel): ViewModel

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


    @Binds
    @IntoMap
    @ViewModelKey(PermissionViewModel::class)
    abstract fun bindsPermissionViewModel(permissionViewModel:
                                              PermissionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetUnitsCloudViewModel::class)
    abstract fun bindsGetUnitsCloudViewModel(getUnitsCloudViewModel:
                                                 GetUnitsCloudViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetProjectsCloudViewModel::class)
    abstract fun bindsGetProjectsCloudViewModel(getProjectsCloudViewModel:
                                                GetProjectsCloudViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InsertProjectsViewModel::class)
    abstract fun bindsInsertProjectsViewModel(insertProjectsViewModel:
                                                  InsertProjectsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetFormsCloudViewModel::class)
    abstract fun bindsGetFormsCloudViewModel(getFormsCloudViewModel:
                                                 GetFormsCloudViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InsertFormsViewModel::class)
    abstract fun bindsInsertFormsViewModel(insertFormsViewModel:
                                               InsertFormsViewModel): ViewModel


}