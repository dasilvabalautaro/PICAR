package com.empoderar.picar.presentation.navigation

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.presentation.data.FormView
import com.empoderar.picar.presentation.data.ProjectView
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.view.activities.*
import com.empoderar.picar.presentation.view.fragments.NewFormFragment
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

    fun showDownload(context: Context) = context
            .startActivity(DownloadActivity.callingIntent(context))

    fun showForms(activity: FragmentActivity,
                     projectView: ProjectView, navigationExtras: Extras){
        val viewPager = activity.findViewById<ViewPager>(R.id.vp_list)
        BaseFragment.proyectView = projectView

        viewPager.currentItem = 1

    }

    fun showDetailForm(activity: FragmentActivity,
                       formView: FormView, navigationExtras: Extras){
        val newForm = NewFormFragment()
        newForm.flagNewForm = false
        newForm.formView = formView
        (activity as MenuActivity).setMenuAddForm(false)
        activity.addFragment(newForm)

    }


    class Extras(val transitionSharedElement: View)
}