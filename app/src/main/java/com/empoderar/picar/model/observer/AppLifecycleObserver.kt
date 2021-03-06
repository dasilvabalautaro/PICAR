package com.empoderar.picar.model.observer

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import android.content.Context
import android.widget.Toast
import javax.inject.Inject

class AppLifecycleObserver @Inject constructor(private val context: Context):
        LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onEnterDestroy(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onEnterCreate(){
        Toast.makeText(context, "Iniciando", Toast.LENGTH_LONG).show()
    }


}