package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.presentation.component.BodyFormAdapter
import com.empoderar.picar.presentation.data.BodyFormView
import com.empoderar.picar.presentation.extension.addDecorationRecycler
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.GetBodyFormViewModel
import kotlinx.android.synthetic.main.view_body_form.*
import javax.inject.Inject

class BodiesFormFragment: BaseFragment() {

    @Inject
    lateinit var bodyFormAdapter: BodyFormAdapter

    private lateinit var getBodyFormViewModel: GetBodyFormViewModel
    var formId: Int? = null
    override fun layoutId() = R.layout.view_body_form


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        getBodyFormViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetBodies)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        getBodyFormViewModel.formId = formId
        getBodyFormViewModel.loadBodyForm()
    }
    private fun handleGetBodies(list: List<BodyFormView>?){
        bodyFormAdapter.collection = list.orEmpty()
    }

    private fun initializeView(){
        rv_body!!.setHasFixedSize(true)
        rv_body!!.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL, false)
        addDecorationRecycler(rv_body, context!!)
        rv_body.adapter = bodyFormAdapter
        /*photoAdapter.clickListener = { photo, navigationExtras ->
            navigator.showForms(activity!!, photo, navigationExtras) }*/

    }

    override fun renderFailure(message: Int) {
        notify(message)
    }

}