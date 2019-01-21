package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.presentation.component.ListingsPagesAdapter
import com.empoderar.picar.presentation.data.UnityView
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.GetUnitsViewModel
import kotlinx.android.synthetic.main.view_listings.*
import javax.inject.Inject

class ListingsFragment: BaseFragment() {
    @Inject
    lateinit var navigator: Navigator

    private lateinit var getUnitsViewModel: GetUnitsViewModel

    override fun layoutId() = R.layout.view_listings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        getUnitsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetUnits)
            failure(failure, ::handleFailure)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vp_list!!.adapter = ListingsPagesAdapter(activity!!
                .supportFragmentManager, vp_list!!)

        tl_options!!.setupWithViewPager(vp_list)
        tl_options!!.getTabAt(0)!!.text = getString(R.string.lbl_tab_projects)
        tl_options!!.getTabAt(1)!!.text = getString(R.string.lbl_tab_forms)

        loadFormsList()
    }

    override fun onResume() {
        super.onResume()
        vp_list!!.postDelayed({ vp_list!!.setCurrentItem(0,
                true) }, 100)
    }

    override fun renderFailure(message: Int) {
        notify(message)
    }

    private fun loadFormsList(){
        getUnitsViewModel.loadUnities()

    }

    private fun handleGetUnits(list: List<UnityView>?){
        listUnity = list.orEmpty()
    }
}