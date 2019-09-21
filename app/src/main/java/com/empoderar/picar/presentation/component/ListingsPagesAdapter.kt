package com.empoderar.picar.presentation.component

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.empoderar.picar.presentation.view.fragments.BodiesFormFragment
import com.empoderar.picar.presentation.view.fragments.FormsFragment
import com.empoderar.picar.presentation.view.fragments.NewFormFragment
import com.empoderar.picar.presentation.view.fragments.ProjectsFragment

class ListingsPagesAdapter(manager: FragmentManager, pager: ViewPager):
        FragmentStatePagerAdapter(manager){
    var projects: ProjectsFragment? = null
    var forms: FormsFragment? = null
    var newForm: NewFormFragment? = null
    var detail: BodiesFormFragment? = null

    private val numberFragments = 4

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {
                if (projects == null) projects = ProjectsFragment.newInstance()
                return projects!!
            }

            1 -> {
                if (forms == null) forms = FormsFragment.newInstance()
                return forms!!
            }

            2 -> {
                if (newForm == null) newForm = NewFormFragment.newInstance()
                return newForm!!
            }

            3 -> {
                if (detail == null) detail = BodiesFormFragment.newInstance()

            }

        }
        return detail!!
    }

    override fun getCount(): Int {
        return numberFragments
    }


}