package com.empoderar.picar.presentation.navigation

import android.content.Context
import com.empoderar.picar.presentation.view.activities.SplashActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
    fun showSplash(context: Context) = context
            .startActivity(SplashActivity.callingIntent(context))

}