package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.model.persistent.caching.Constants
import com.empoderar.picar.model.persistent.preference.PreferenceRepository
import com.empoderar.picar.presentation.component.FormsAdapter
import com.empoderar.picar.presentation.data.FormView
import com.empoderar.picar.presentation.extension.addDecorationRecycler
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.GetFormsCloudViewModel
import com.empoderar.picar.presentation.presenter.GetFormsViewModel
import com.empoderar.picar.presentation.presenter.InsertFormsViewModel
import kotlinx.android.synthetic.main.view_list_form.*

import javax.inject.Inject

class FormsFragment: BaseFragment() {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var formsAdapter: FormsAdapter

    private lateinit var getFormsViewModel: GetFormsViewModel
    private lateinit var getFormsCloudViewModel: GetFormsCloudViewModel
    private lateinit var insertFormsViewModel: InsertFormsViewModel
    private var idFormTemp = -1

    override fun layoutId() = R.layout.view_list_form

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        getFormsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetForms)
            failure(failure, ::handleFailure)
        }
        getFormsCloudViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleFormsCloud)
            failure(failure, ::handleFailure)
        }

        insertFormsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleInsertForms)
            failure(failure, ::handleFailure)
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        setupSwipeRefresh()
        //loadFormsList()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        getFormsOfCloud()
    }

    private fun getFormsOfCloud(){
        //showProgress()
        if (proyectView != null){
            val url = String.format(Constants.urlBase +
                    "${Constants.serviceFormsByProject}${proyectView!!.id}")
            val prefs = PreferenceRepository.customPrefs(activity!!,
                    Constants.preference_picar)
            val token = "Bearer " + prefs.getString(Constants.prefToken, "")
            getFormsCloudViewModel.url = url
            getFormsCloudViewModel.token = token
            if (getFormsCloudViewModel.verifyInput()){
                getFormsCloudViewModel.requestForms()
            }

        }
    }

    private fun loadFormsList(){
        getFormsViewModel.loadForms()

    }

    private fun handleGetForms(list: List<FormView>?){
        listForm = list.orEmpty()
        setListFormByProject()
    }

    private fun setListFormByProject(){
        if (!listForm.isNullOrEmpty()){
            val subList = listForm!!
                    .filter { it.project == proyectView!!.id }
            formsAdapter.collection = subList
        }else{
            formsAdapter.collection = listForm.orEmpty()
        }
        sr_forms!!.isRefreshing = false
    }

    private fun handleFormsCloud(list: List<Form>?){
        if (list != null && list.isNotEmpty()){
            insertFormsViewModel .list = list
            insertFormsViewModel .insertForms()
        }

    }

    private fun handleInsertForms(value: Boolean?){
        //hideProgress()
        if (value != null && value){
            loadFormsList()

        }
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
            this::setListFormByProject)
}