package com.empoderar.picar.presentation.plataform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.empoderar.picar.model.exception.Failure

abstract class BaseViewModel : ViewModel() {

    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }
}