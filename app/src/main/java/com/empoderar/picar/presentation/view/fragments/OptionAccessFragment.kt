package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.presentation.data.MunicipalityView
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.GetMunicipalitiesViewModel
import com.empoderar.picar.presentation.view.activities.OptionAccessActivity
import kotlinx.android.synthetic.main.view_options_access.*
import javax.inject.Inject

class OptionAccessFragment: BaseFragment() {
    @Inject
    lateinit var navigator: Navigator

    private lateinit var getMunicipalitiesViewModel: GetMunicipalitiesViewModel

    override fun layoutId() = R.layout.view_options_access

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        getMunicipalitiesViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetMunicipalities)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ib_forms.setOnClickListener{
            navigator.showLogin(requireActivity())
            (requireActivity() as OptionAccessActivity).finish()
        }
        loadMunicipalityList()
    }

    override fun renderFailure(message: Int) {
        notify(message)
    }

    private fun handleGetMunicipalities(list: List<MunicipalityView>?){
        listMunicipality = list.orEmpty()
    }

    private fun loadMunicipalityList(){
        getMunicipalitiesViewModel.loadMunicipalities()
    }

}