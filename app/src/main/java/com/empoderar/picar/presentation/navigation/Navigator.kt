package com.empoderar.picar.presentation.navigation

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.presentation.data.ProjectView
import com.empoderar.picar.presentation.plataform.BaseFragment
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

    fun showForms(activity: FragmentActivity,
                     projectView: ProjectView, navigationExtras: Extras){
        val viewPager = activity.findViewById<ViewPager>(R.id.vp_list)
        BaseFragment.proyectView = projectView
        viewPager.currentItem = 1

    }

    class Extras(val transitionSharedElement: View)
}