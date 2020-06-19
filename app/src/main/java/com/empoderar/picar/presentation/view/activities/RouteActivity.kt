package com.empoderar.picar.presentation.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.empoderar.picar.App
import com.empoderar.picar.di.ApplicationComponent
import com.empoderar.picar.presentation.navigation.Navigator
import javax.inject.Inject

class RouteActivity : AppCompatActivity() {

    private val appComponent: ApplicationComponent by
    lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as App).appComponent
    }

    @Inject
    internal lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        navigator.showSplash(this)
    }
}
