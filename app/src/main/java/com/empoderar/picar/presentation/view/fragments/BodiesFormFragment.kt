package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.empoderar.picar.R
import com.empoderar.picar.domain.data.BodyForm
import com.empoderar.picar.presentation.component.BodyFormAdapter
import com.empoderar.picar.presentation.data.BodyFormView
import com.empoderar.picar.presentation.extension.addDecorationRecycler
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.GetBodyFormViewModel
import com.empoderar.picar.presentation.presenter.InsertBodiesFormViewModel
import com.empoderar.picar.presentation.tools.Transform
import kotlinx.android.synthetic.main.view_body_form.*
import javax.inject.Inject

class BodiesFormFragment: BaseFragment() {

    companion object{
        private lateinit var getBodyFormViewModel: GetBodyFormViewModel
        private lateinit var insertBodiesFormViewModel: InsertBodiesFormViewModel
        private var listBodiesView: List<BodyFormView>? = null
        var formId: Int = 0

        fun getBodies(){
            if (formId != 0){
                getBodyFormViewModel.formId = formId
                getBodyFormViewModel.loadBodyForm()
            }
        }

        fun updateValue(newValue: String, id: Int){
            listBodiesView!!.find {it.id == id}!!.value = newValue
        }

        fun updateSatisfy(newSatisfy: String, id: Int){
            listBodiesView!!.find {it.id == id}!!.satisfy = newSatisfy
        }

        fun updateDate(newDate: String, id: Int){
            val dateLong = Transform.convertDateToLong(newDate)
            //val dateToCloud = String.format(Locale.US,"/Date(%d)/", dateLong)
            val dateToCloud = dateLong.toString()
            listBodiesView!!.find {it.id == id}!!.date = dateToCloud
        }

        fun updateComment(newComment: String, id: Int){
            listBodiesView!!.find {it.id == id}!!.comment = newComment
        }

        fun insertBodies(){

            if (!listBodiesView.isNullOrEmpty()){
                val list = listBodiesView!!.map {
                    BodyForm(it.id, it.formId,
                            it.idProject, it.code!!, it.value!!,
                            it.description!!, it.satisfy!!,
                            it.date!!, it.comment!!)
                }
                insertBodiesFormViewModel.list = list.toList()
                insertBodiesFormViewModel.insertBodiesForm()
            }

        }

        @JvmStatic
        fun newInstance()=BodiesFormFragment()
    }

    @Inject
    lateinit var bodyFormAdapter: BodyFormAdapter


    override fun layoutId() = R.layout.view_body_form


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBodyFormViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetBodies)
            failure(failure, ::handleFailure)
        }

        insertBodiesFormViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleInsertBodies)
            failure(failure, ::handleFailure)
        }

        initializeView()
        getBodies()
    }


    override fun onResume() {
        super.onResume()
        if (formId == 0){
            bodyFormAdapter.collection.toMutableList().clear()
        }

    }
    private fun handleGetBodies(list: List<BodyFormView>?){
        listBodiesView = list
        bodyFormAdapter.collection = list.orEmpty()
    }

    private fun handleInsertBodies(value: Boolean?){
        if (value != null && value){
            requireContext().toast("Insert Bodies OK")
        }
    }

    private fun initializeView(){
        rv_body!!.setHasFixedSize(true)
        rv_body!!.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL, false)
        addDecorationRecycler(rv_body, requireContext())
        rv_body.adapter = bodyFormAdapter

        /*photoAdapter.clickListener = { photo, navigationExtras ->
            navigator.showForms(activity!!, photo, navigationExtras) }*/

    }

    override fun onPause() {
        super.onPause()
        insertBodies()

    }

    override fun renderFailure(message: Int) {
        notify(message)
    }

}