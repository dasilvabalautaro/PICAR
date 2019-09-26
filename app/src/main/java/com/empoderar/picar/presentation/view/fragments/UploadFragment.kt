package com.empoderar.picar.presentation.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.domain.data.Message
import com.empoderar.picar.model.persistent.caching.Constants
import com.empoderar.picar.model.persistent.preference.PreferenceRepository
import com.empoderar.picar.presentation.data.BodyFormView
import com.empoderar.picar.presentation.data.FormView
import com.empoderar.picar.presentation.data.ImageView
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.*
import kotlinx.android.synthetic.main.view_down_progress.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.runOnUiThread

class UploadFragment: BaseFragment() {

    private lateinit var uploadFormsViewModel: UploadFormsViewModel
    private lateinit var getFormsViewModel: GetFormsViewModel
    private lateinit var updateUploadFormsViewModel: UpdateUploadAllFormsViewModel
    private lateinit var getImagesByFormViewModel: GetImagesByFormViewModel
    private lateinit var uploadImagesViewModel: UploadImagesViewModel
    private lateinit var getBodyFormViewModel: GetBodyFormViewModel
    private lateinit var uploadBodiesViewModel: UploadBodiesViewModel


    private lateinit var prefs: SharedPreferences

    override fun layoutId() = R.layout.view_down_progress
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        getFormsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetForms)
            failure(failure, ::handleFailure)
        }

        uploadFormsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleUploadForms)
            failure(failure, ::handleFailure)
        }

        updateUploadFormsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleUpdateForms)
            failure(failure, ::handleFailure)
        }

        getImagesByFormViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetImagesByForm)
            failure(failure, ::handleFailure)
        }

        uploadImagesViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleUploadImagesByForm)
            failure(failure, ::handleFailure)
        }

        getBodyFormViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetBodiesByForm)
            failure(failure, ::handleFailure)
        }

        uploadBodiesViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleUploadBodiesByForm)
            failure(failure, ::handleFailure)
        }

        this.prefs = PreferenceRepository.customPrefs(activity!!,
                Constants.preference_picar)

        getFormsViewModel.upload = 1
        getFormsViewModel.loadForms()
    }

    private fun handleUploadImagesByForm(result: Message?){
        if (result != null){
            context!!.toast(result.message)
        }
    }

    private fun handleUploadBodiesByForm(result: Message?){
        if (result != null){
            context!!.toast(result.message)
        }
    }

    private fun handleGetImagesByForm(list: List<ImageView>?){
        if (!list.isNullOrEmpty()){
            uploadImagesToCloud(list)
        }
    }

    private fun handleGetBodiesByForm(list: List<BodyFormView>?){
        if (!list.isNullOrEmpty()){
            uploadBodiesToCloud(list)
        }
    }

    private fun handleGetForms(list: List<FormView>?){
        listForm = list.orEmpty()

        if (!list.isNullOrEmpty()){
            println("Forms Upload: " + listForm!!.count().toString())
            getFormDataInDetail(listForm!!)
            uploadFormsToCloud(listForm!!)
        }else{
            context!!.toast("Not Forms for Upload")
        }
    }

    private fun getFormDataInDetail(list: List<FormView>)
    {
        GlobalScope.launch(Dispatchers.Default) {
            getImagesOfListForm(list)
            getBodiesOfListForm(list)
        }

    }

    private fun getImagesOfListForm(list: List<FormView>){
        for (form:FormView in list){

            getImagesByFormViewModel.idForm = form.id
            getImagesByFormViewModel.loadImages()

            Thread.sleep(500)
        }


    }

    private fun getBodiesOfListForm(list: List<FormView>){
        for (form:FormView in list){

            getBodyFormViewModel.formId = form.id
            getBodyFormViewModel.loadBodyForm()

            Thread.sleep(500)
        }

        Thread.sleep(500)
        runOnUiThread {
            context!!.toast("Send Bodies Finished")
            pb_upload!!.visibility = View.INVISIBLE
            tv_message!!.text = getString(R.string.lbl_finished)
        }

    }


    private fun handleUploadForms(result: Message?){
        if (result != null){
            updateUploadFormsViewModel.updateUploadForms()
            context!!.toast(result.message)
        }
    }

    private fun handleUpdateForms(value: Boolean?){
        if (value != null && value){
            println("Update Ok")
            context!!.toast("Update Database Finished")
        }
    }

    private fun uploadFormsToCloud(list: List<FormView>){
        //showProgress()
        val url = String.format("${Constants.urlBase}${Constants.serviceUploadForms}")
        val token = "Bearer " + this.prefs.getString(Constants.prefToken, "")
        uploadFormsViewModel.url = url
        uploadFormsViewModel.token = token
        uploadFormsViewModel.forms = list
        if (uploadFormsViewModel.buildParams()){
            uploadFormsViewModel.requestPost()
        }
    }

    private fun uploadImagesToCloud(list: List<ImageView>){
        val url = String.format("${Constants.urlBase}${Constants.serviceUploadImages}")
        val token = "Bearer " + this.prefs.getString(Constants.prefToken, "")
        uploadImagesViewModel.url = url
        uploadImagesViewModel.token = token
        uploadImagesViewModel.images = list
        if (uploadImagesViewModel.buildParams()){
            uploadImagesViewModel.requestPost()
        }
    }

    private fun uploadBodiesToCloud(list: List<BodyFormView>){
        val url = String.format("${Constants.urlBase}${Constants.serviceUploadBodies}")
        val token = "Bearer " + this.prefs.getString(Constants.prefToken, "")
        uploadBodiesViewModel.url = url
        uploadBodiesViewModel.token = token
        uploadBodiesViewModel.bodies = list
        if (uploadBodiesViewModel.buildParams()){
            uploadBodiesViewModel.requestPost()
        }
    }

    override fun renderFailure(message: Int) {
        notify(message)
        pb_upload!!.visibility = View.INVISIBLE
        tv_message!!.text = getString(R.string.lbl_finished)

    }

}