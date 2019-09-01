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

    @Binds
    @IntoMap
    @ViewModelKey(InsertOneFormViewModel::class)
    abstract fun bindsInsertOneFormViewModel(insertOneFormViewModel:
                                                 InsertOneFormViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InsertImagesViewModel::class)
    abstract fun bindsInsertImagesViewModel(insertImagesViewModel:
                                                InsertImagesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetImagesByFormViewModel::class)
    abstract fun bindsGetImagesByFormViewModel(getImagesByFormViewModel:
                                                   GetImagesByFormViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetTypesFormCloudViewModel::class)
    abstract fun bindsGetTypesFormCloudViewModel(getTypesFormCloudViewModel:
                                                     GetTypesFormCloudViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InsertTypesFormViewModel::class)
    abstract fun bindsInsertTypesFormViewModel(insertTypesFormViewModel:
                                                   InsertTypesFormViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetTypesFormViewModel::class)
    abstract fun bindsGetTypesFormViewModel(getTypesFormViewModel:
                                                GetTypesFormViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetContentFormsCloudViewModel::class)
    abstract fun bindsGetContentFormsCloudViewModel(getContentFormsCloudViewModel:
                                                        GetContentFormsCloudViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InsertContentFormsViewModel::class)
    abstract fun bindsInsertContentFormsViewModel(insertContentFormsViewModel:
                                                      InsertContentFormsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetContentFormsViewModel::class)
    abstract fun bindsGetContentFormsViewModel(getContentFormsViewModel:
                                                   GetContentFormsViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(InsertUnitsViewModel::class)
    abstract fun bindsInsertUnitsViewModel(insertUnitsViewModel:
                                               InsertUnitsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InsertBodiesFormViewModel::class)
    abstract fun bindsInsertBodiesFormViewModel(insertBodiesFormViewModel:
                                                    InsertBodiesFormViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetBodyFormViewModel::class)
    abstract fun bindsGetBodyFormViewModel(getBodyFormViewModel:
                                               GetBodyFormViewModel): ViewModel


}