package com.empoderar.picar.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.empoderar.picar.presentation.presenter.DownUserViewModel
import com.empoderar.picar.presentation.presenter.InsertUsersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

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

}