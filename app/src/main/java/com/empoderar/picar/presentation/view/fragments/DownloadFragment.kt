package com.empoderar.picar.presentation.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import com.empoderar.picar.R
import com.empoderar.picar.domain.data.*
import com.empoderar.picar.model.persistent.caching.Constants
import com.empoderar.picar.model.persistent.preference.PreferenceRepository
import com.empoderar.picar.model.persistent.preference.PreferenceRepository.set
import com.empoderar.picar.presentation.extension.empty
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.*
import com.empoderar.picar.presentation.view.activities.DownloadActivity
import kotlinx.coroutines.*
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.toast

import javax.inject.Inject

class DownloadFragment: BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var getUnitsCloudViewModel: GetUnitsCloudViewModel
    private lateinit var getProjectsCloudViewModel: GetProjectsCloudViewModel
    private lateinit var insertProjectsViewModel: InsertProjectsViewModel
    private lateinit var insertUnitsViewModel: InsertUnitsViewModel
    private lateinit var getFormsCloudViewModel: GetFormsCloudViewModel
    private lateinit var insertFormsViewModel: InsertFormsViewModel
    private lateinit var getTypesFormCloudViewModel: GetTypesFormCloudViewModel
    private lateinit var insertTypesFormViewModel: InsertTypesFormViewModel
    private lateinit var getContentFormsCloudViewModel: GetContentFormsCloudViewModel
    private lateinit var insertContentFormsViewModel: InsertContentFormsViewModel


    private lateinit var prefs: SharedPreferences
    private var listProjectDownload: MutableList<Project>? = null
    private var jobComplete = false

    override fun layoutId() = R.layout.view_down_progress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        getUnitsCloudViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleCloudUnits)
            failure(failure, ::handleFailure)
        }

        getProjectsCloudViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleProjectsCloud)
            failure(failure, ::handleFailure)
        }

        insertProjectsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleInsertProjects)
            failure(failure, ::handleFailure)
        }

        insertUnitsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleInsertUnits)
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

        getTypesFormCloudViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleTypesFormsCloud)
            failure(failure, ::handleFailure)
        }

        insertTypesFormViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleInsertTypesForms)
            failure(failure, ::handleFailure)
        }

        getContentFormsCloudViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleContentFormsCloud)
            failure(failure, ::handleFailure)
        }

        insertContentFormsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleInsertContentForms)
            failure(failure, ::handleFailure)
        }

        this.prefs = PreferenceRepository.customPrefs(activity!!,
                Constants.preference_picar)

        GlobalScope.async{
            tiredOfWaiting()
        }

        getUnitsOfCloud()
    }

    private fun handleProjectsCloud(list: List<Project>?){
        if (list != null && list.isNotEmpty()){
            listProjectDownload = list.toMutableList()
            insertProjectsViewModel.list = list
            insertProjectsViewModel.insertProjects()

        }
    }

    private fun handleInsertProjects(value: Boolean?){
        //hideProgress()
        if (value != null && value){
            this.prefs[Constants.prefIsProjectsDownload] = 1
            getTypesFormOfCloud()
            Thread.sleep(1000)
            initDownloadForms()

        }
    }

    private fun handleCloudUnits(list: List<Unity>?){
        //hideProgress()
        if (list != null && list.isNotEmpty()){
            insertUnitsViewModel.list = list
            insertUnitsViewModel.insertUnits()
        }
    }

    private fun handleInsertUnits(value: Boolean?){
        //hideProgress()
        if (value != null && value){
            this.prefs[Constants.prefIsUnitiesDownload] = 1
            getProjectsOfCloud()

        }
    }

    private fun handleFormsCloud(list: List<Form>?){
        if (list != null && list.isNotEmpty()){
            insertFormsViewModel .list = list
            insertFormsViewModel .insertForms()
        }

    }

    private fun handleInsertForms(value: Boolean?){
        //hideProgress()
        if (value != null && value && isFinishDownloadForms()){

            this.prefs[Constants.prefIsFormsDownload] = 1
            verifyDownload()
            jobComplete = true
//            navigator.showMenu(activity!!)
//            (activity!! as DownloadActivity).finish()

        }
    }

    private fun handleTypesFormsCloud(list: List<TypeForm>?){
        if (list != null && list.isNotEmpty()){
            insertTypesFormViewModel.list = list
            insertTypesFormViewModel.insertTypesForm()
        }
    }


    private fun handleInsertTypesForms(value: Boolean?){
        //hideProgress()
        if (value != null && value){

            this.prefs[Constants.prefIsTypesFormsDownload] = 1
            getContentFormOfCloud()

        }
    }

    private fun handleContentFormsCloud(list: List<ContentForm>?){
        if (list != null && list.isNotEmpty()){
            insertContentFormsViewModel.list = list
            insertContentFormsViewModel.insertContents()
        }
    }

    private fun handleInsertContentForms(value: Boolean?){
        //hideProgress()
        if (value != null && value){

            this.prefs[Constants.prefIsContentFormsDownload] = 1

        }
    }


    private fun getUnitsOfCloud(){
        //showProgress()
        val url = String.format("${Constants.urlBase}${Constants.serviceUnit}")
        val token = "Bearer " + this.prefs.getString(Constants.prefToken, "")
        getUnitsCloudViewModel.url = url
        getUnitsCloudViewModel.token = token
        if (getUnitsCloudViewModel.verifyInput()){
            getUnitsCloudViewModel.requestUnits()
        }
    }

    private fun getProjectsOfCloud(){
        //showProgress()
        val url = String.format("${Constants.urlBase}${Constants.serviceProjectsByUnit}")
        val token = "Bearer " + this.prefs.getString(Constants.prefToken, "")
        getProjectsCloudViewModel.url = url
        getProjectsCloudViewModel.token = token
        if (getProjectsCloudViewModel.verifyInput()){
            getProjectsCloudViewModel.requestProjects()
        }
    }

    private fun initDownloadForms(){

        if (listProjectDownload!!.isNotEmpty()){
            val index = listProjectDownload!!.lastIndex
            val id = listProjectDownload!![index].id
            listProjectDownload!!.removeAt(index)
            getFormsOfCloud(id)

        }
    }

    private fun isFinishDownloadForms(): Boolean{
        return if (listProjectDownload!!.isNotEmpty()){
            val index = listProjectDownload!!.lastIndex
            val id = listProjectDownload!![index].id
            listProjectDownload!!.removeAt(index)
            getFormsOfCloud(id)
            false
        }else{
            true
        }

    }

    private fun getFormsOfCloud(id: Int){
        //showProgress()

        val url = String.format(Constants.urlBase +
                "${Constants.serviceFormsByProject}${id}")
        val token = "Bearer " + this.prefs.getString(Constants.prefToken, "")
        getFormsCloudViewModel.url = url
        getFormsCloudViewModel.token = token
        if (getFormsCloudViewModel.verifyInput()){
            getFormsCloudViewModel.requestForms()
        }


    }

    private fun getTypesFormOfCloud(){
        //showProgress()
        val url = String.format("${Constants.urlBase}${Constants.serviceTypesForm}")
        val token = "Bearer " + this.prefs.getString(Constants.prefToken, "")
        getTypesFormCloudViewModel.url = url
        getTypesFormCloudViewModel.token = token
        if (getTypesFormCloudViewModel.verifyInput()){
            getTypesFormCloudViewModel.requestTypesForm()
        }
    }

    private fun getContentFormOfCloud(){
        //showProgress()
        val url = String.format("${Constants.urlBase}${Constants.serviceContentForms}")
        val token = "Bearer " + this.prefs.getString(Constants.prefToken, "")
        getContentFormsCloudViewModel.url = url
        getContentFormsCloudViewModel.token = token
        if (getContentFormsCloudViewModel.verifyInput()){
            getContentFormsCloudViewModel.requestContentForms()
        }
    }

    private fun verifyDownload(){
        var result = String.empty()
        var isDown = this.prefs.getInt(Constants.prefIsUnitiesDownload, 0)
        if (isDown == 0){
            result += getString(R.string.msg_error_download_unities) + "\n"
        }
        isDown = this.prefs.getInt(Constants.prefIsProjectsDownload, 0)
        if (isDown == 0){
            result += getString(R.string.msg_error_download_projects) + "\n"
        }
        isDown = this.prefs.getInt(Constants.prefIsFormsDownload, 0)
        if (isDown == 0){
            result += getString(R.string.msg_error_download_forms) + "\n"
        }
        isDown = this.prefs.getInt(Constants.prefIsTypesFormsDownload, 0)
        if (isDown == 0){
            result += getString(R.string.msg_error_download_types_forms) + "\n"
        }
        isDown = this.prefs.getInt(Constants.prefIsContentFormsDownload, 0)
        if (isDown == 0){
            result += getString(R.string.msg_error_download_content_forms) + "\n"
        }

        if (result.isEmpty()){
            result = getString(R.string.msg_download)
        }

        toast(result)

    }

    private fun tiredOfWaiting() = runBlocking{
        val startTime = System.currentTimeMillis()
        val job = GlobalScope.launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 8 && !jobComplete) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L)
        job.cancelAndJoin()
        println("Now I can quit.")
        runOnUiThread {
            navigator.showMenu(activity!!)
            (activity!! as DownloadActivity).finish()
        }

    }

    override fun renderFailure(message: Int) {
        notify(message)
    }




}