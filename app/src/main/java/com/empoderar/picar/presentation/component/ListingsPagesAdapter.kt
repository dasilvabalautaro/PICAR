package com.empoderar.picar.presentation.component

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.empoderar.picar.presentation.view.fragments.ProjectsFragment

class ListingsPagesAdapter(manager: FragmentManager, pager: ViewPager):
        FragmentStatePagerAdapter(manager) {

    var projects: ProjectsFragment? = null
    private val numberFragments = 2

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {
                if (projects == null) projects = ProjectsFragment()
                return projects!!
            }
        }
        return projects!!
    }

    override fun getCount(): Int {
        return numberFragments
    }


}