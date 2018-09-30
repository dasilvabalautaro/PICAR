package com.empoderar.picar.presentation.plataform

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.empoderar.picar.model.exception.Failure

abstract class BaseViewModel : ViewModel() {

    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }
}