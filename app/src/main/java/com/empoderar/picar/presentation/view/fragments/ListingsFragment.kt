package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.domain.data.Unity
import com.empoderar.picar.presentation.component.ListingsPagesAdapter
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.GetUnitsDatabaseViewModel
import com.empoderar.picar.presentation.view.activities.MenuActivity
import kotlinx.android.synthetic.main.view_listings.*
import javax.inject.Inject

class ListingsFragment: BaseFragment(){

    @Inject
    lateinit var navigator: Navigator


    override fun layoutId() = R.layout.view_listings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vp_list!!.adapter = ListingsPagesAdapter(activity!!
                .supportFragmentManager, vp_list!!)

        tl_options!!.setupWithViewPager(vp_list)
        tl_options!!.getTabAt(0)!!.text = getString(R.string.lbl_tab_projects)
        tl_options!!.getTabAt(1)!!.text = getString(R.string.lbl_tab_forms)
        tl_options!!.getTabAt(2)!!.text = getString(R.string.lbl_tab_new)
        tl_options!!.getTabAt(3)!!.text = getString(R.string.lbl_tab_detail)

        vp_list?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                if (position == 2) (activity as MenuActivity).setOrientation(true)
                if (position == 3) (activity as MenuActivity).setOrientation(false)

            }

        })

    }

    /*override fun onResume() {
        super.onResume()
        vp_list!!.postDelayed({ vp_list!!.setCurrentItem(0,
                true) }, 100)
    }*/

    override fun renderFailure(message: Int) {
        notify(message)
    }


}