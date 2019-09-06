package com.empoderar.picar.presentation.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import com.empoderar.picar.R
import com.empoderar.picar.domain.data.Message
import com.empoderar.picar.model.persistent.caching.Constants
import com.empoderar.picar.model.persistent.network.entity.MessageEntity
import com.empoderar.picar.model.persistent.preference.PreferenceRepository
import com.empoderar.picar.presentation.data.FormView
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.GetFormsViewModel
import com.empoderar.picar.presentation.presenter.UpdateUploadAllFormsViewModel
import com.empoderar.picar.presentation.presenter.UploadFormsViewModel

class UploadFragment: BaseFragment() {

    private lateinit var uploadFormsViewModel: UploadFormsViewModel
    private lateinit var getFormsViewModel: GetFormsViewModel
    private lateinit var updateUploadFormsViewModel: UpdateUploadAllFormsViewModel
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
        this.prefs = PreferenceRepository.customPrefs(activity!!,
                Constants.preference_picar)

        getFormsViewModel.upload = 1
        getFormsViewModel.loadForms()
    }

    private fun handleGetForms(list: List<FormView>?){
        listForm = list.orEmpty()

        if (!list.isNullOrEmpty()){
            println("Forms Upload: " + listForm!!.count().toString())
            uploadFormsToCloud(listForm!!)
        }else{
            context!!.toast("Not Forms for Upload")
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
            context!!.toast("Upload Finished")
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

    override fun renderFailure(message: Int) {
        notify(message)
    }

}