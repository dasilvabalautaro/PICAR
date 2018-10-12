package com.empoderar.picar.presentation.navigation

import android.content.Context
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.presentation.view.activities.LoginActivity
import com.empoderar.picar.presentation.view.activities.MenuActivity
import com.empoderar.picar.presentation.view.activities.OptionAccessActivity
import com.empoderar.picar.presentation.view.activities.SplashActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
    fun showSplash(context: Context) = context
            .startActivity(SplashActivity.callingIntent(context))

    fun showOptions(context: Context) = context
            .startActivity(OptionAccessActivity.callingIntent(context))

    fun showItemMenu(itemSelect: String, context: Context){
        /*when(itemSelect){
            context.getString(R.string.item_menu_1) ->
        }*/
    }

    fun showLogin(context: Context) = context
            .startActivity(LoginActivity.callingIntent(context))

    fun showMenu(context: Context) = context
            .startActivity(MenuActivity.callingIntent(context))


    class Extras(val transitionSharedElement: View)
}