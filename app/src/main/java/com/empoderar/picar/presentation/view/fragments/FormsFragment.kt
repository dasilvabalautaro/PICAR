package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.presentation.component.FormsAdapter
import com.empoderar.picar.presentation.data.FormView
import com.empoderar.picar.presentation.extension.addDecorationRecycler
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.GetFormsViewModel
import kotlinx.android.synthetic.main.view_list_form.*

import javax.inject.Inject

class FormsFragment: BaseFragment() {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var formsAdapter: FormsAdapter

    private lateinit var getFormsViewModel: GetFormsViewModel

    override fun layoutId() = R.layout.view_list_form

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        getFormsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetForms)
            failure(failure, ::handleFailure)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        setupSwipeRefresh()
        loadFormsList()
    }

    private fun loadFormsList(){
        getFormsViewModel.loadForms()
        sr_forms!!.isRefreshing = false
    }

    private fun handleGetForms(list: List<FormView>?){
        formsAdapter.collection = list.orEmpty()

    }

    private fun initializeView(){
        rv_forms!!.setHasFixedSize(true)
        rv_forms!!.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL, false)
        addDecorationRecycler(rv_forms, context!!)
        rv_forms.adapter = formsAdapter
        /* projectsAdapter.clickListener = { project, navigationExtras ->
             navigator.showMovieDetails(activity!!, project, navigationExtras) }*/
    }

    override fun renderFailure(message: Int) {
        notify(message)
    }

    private fun setupSwipeRefresh() = sr_forms!!.setOnRefreshListener(
            this::loadFormsList)
}