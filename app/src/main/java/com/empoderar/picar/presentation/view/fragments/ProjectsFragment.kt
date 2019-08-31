package com.empoderar.picar.presentation.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.empoderar.picar.R
import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.data.Project
import com.empoderar.picar.domain.data.Unity
import com.empoderar.picar.model.persistent.caching.Constants
import com.empoderar.picar.model.persistent.preference.PreferenceRepository
import com.empoderar.picar.model.persistent.preference.PreferenceRepository.set
import com.empoderar.picar.presentation.component.ProjectsAdapter
import com.empoderar.picar.presentation.data.ProjectView
import com.empoderar.picar.presentation.extension.addDecorationRecycler
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.*
import com.empoderar.picar.presentation.view.activities.MenuActivity
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Cancellable
import kotlinx.android.synthetic.main.view_list_project.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProjectsFragment: BaseFragment() {
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var projectsAdapter: ProjectsAdapter

    //private lateinit var getUnitsCloudViewModel: GetUnitsCloudViewModel
    private lateinit var getUnitsViewModel: GetUnitsDatabaseViewModel
    private var namesUnits: ArrayList<String> = ArrayList()
    private lateinit var getProjectsViewModel: GetProjectsDatabaseViewModel
    //private lateinit var getProjectsCloudViewModel: GetProjectsCloudViewModel
    //private lateinit var insertProjectsViewModel: InsertProjectsViewModel
    //private lateinit var getFormsCloudViewModel: GetFormsCloudViewModel
    //private lateinit var insertFormsViewModel: InsertFormsViewModel
    private var idUnityTemp = -1
    private lateinit var prefs: SharedPreferences


    override fun layoutId() = R.layout.view_list_project
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        getProjectsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetProjects)
            failure(failure, ::handleFailure)
        }

        /*getUnitsCloudViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleCloudUnits)
            failure(failure, ::handleFailure)
        }*/
        getUnitsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetUnits)
            failure(failure, ::handleFailure)
        }

    /*    getProjectsCloudViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleProjectsCloud)
            failure(failure, ::handleFailure)
        }

        insertProjectsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleInsertProjects)
            failure(failure, ::handleFailure)
        }

        getFormsCloudViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleFormsCloud)
            failure(failure, ::handleFailure)
        }

        insertFormsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleInsertForms)
            failure(failure, ::handleFailure)
        }*/

        this.prefs = PreferenceRepository.customPrefs(activity!!,
                Constants.preference_picar)

        loadUnitsDatabase()
        loadProjectList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ib_refresh.setOnClickListener { loadUnitsDatabase() }
        initializeView()
        setupSwipeRefresh()

    }

    override fun onStart() {
        super.onStart()
        disposable.add( actionOnItemSelectedListenerObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .map { position ->
                    run{

                        val nameUnity = sp_select!!
                                .getItemAtPosition(position) as String

                        this.idUnityTemp = getIdUnityOfList(nameUnity)
                        setListProjectForUnity()

                        return@map resources.getString(R.string.new_filter)
                    }
                }
                .subscribe { result -> println(result)})
    }

  /*  private fun verifyLoadOfUnities(){
        val isDown = this.prefs.getInt(Constants.prefIsUnitiesDownload, 0)
        if (isDown == 0){
            getUnitsOfCloud()
        }else{
            loadUnitsDatabase()
        }
    }*/

   /* private fun verifyLoadOfProjects(){
        val isDown = this.prefs.getInt(Constants.prefIsProjectsDownload, 0)
        if (isDown == 0){
            getProjectsOfCloud()
        }else{
            loadProjectList()
        }

    }*/

    /*private fun handleProjectsCloud(list: List<Project>?){
        if (list != null && list.isNotEmpty()){
            insertProjectsViewModel.list = list
            insertProjectsViewModel.insertProjects()
            getFormsOfCloud(list)
        }
    }
*/
   /* private fun getFormsOfCloud(list: List<Project>){
        GlobalScope.launch{
            for (i in 0 until list.count()){
                val id = list[i].id
                getFormsOfCloud(id)
                delay(1000)

            }
        }

    }
*/
   /* private fun handleInsertProjects(value: Boolean?){
        //hideProgress()
        if (value != null && value){
            this.prefs[Constants.prefIsProjectsDownload] = 1
            loadProjectList()

        }
    }*/

    private fun loadProjectList(){
        getProjectsViewModel.loadProjects()

    }

    private fun handleGetProjects(list: List<ProjectView>?){

        listProject = list.orEmpty()
        projectsAdapter.collection = list.orEmpty()

    }

    private fun setListProjectForUnity(){
        if (!listProject.isNullOrEmpty()){
            if (this.idUnityTemp != -1){
                val subList = listProject!!
                        .filter { it.unity == this.idUnityTemp }
                projectsAdapter.collection = subList

            }else{
                projectsAdapter.collection = listProject!!
            }
        }
        sr_projects!!.isRefreshing = false
    }

    private fun getIdUnityOfList(name: String): Int{
        var id = 0
        if (!listUnity.isNullOrEmpty()){
            val unity = listUnity!!.first { it.name == name }
            id = unity.id
        }
        return id
    }

  /*  private fun handleCloudUnits(value: Boolean?){
        //hideProgress()
        if (value != null && value){
            Thread.sleep(2000)
            this.prefs[Constants.prefIsUnitiesDownload] = 1
            loadUnitsDatabase()
        }
    }*/

   /* private fun getUnitsOfCloud(){
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
*/
    private fun loadUnitsDatabase(){
        getUnitsViewModel.loadUnities()

    }

    private fun handleGetUnits(list: List<Unity>?){
        listUnity = list.orEmpty()
        loadUnits()

    }
    private fun initializeView(){
        rv_projects!!.setHasFixedSize(true)
        rv_projects!!.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL, false)
        addDecorationRecycler(rv_projects, context!!)
        rv_projects.adapter = projectsAdapter
        projectsAdapter.clickListener = { project, navigationExtras ->
            navigator.showForms(activity!!, project, navigationExtras) }
    }

    override fun renderFailure(message: Int) {
        //hideProgress()
        notify(message)
    }

    private fun setupSwipeRefresh() = sr_projects!!.setOnRefreshListener(
            this::setListProjectForUnity)

    private fun loadUnits(){
        if (listUnity != null && listUnity!!.isNotEmpty()){
            loadNamesUnits(listUnity!!)
            setDataSpinner()
        }
    }

    private fun loadNamesUnits(list: List<Unity>){
        for (u: Unity in list){
            this.namesUnits.add(u.name)

        }
    }

    private fun setDataSpinner(){

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(context!!,
                android.R.layout.simple_list_item_1, this.namesUnits)
        sp_select!!.adapter = spinnerAdapter
        spinnerAdapter.notifyDataSetChanged()
        if (sp_select!!.adapter != null){
            sp_select!!.refreshDrawableState()
        }

    }

    private fun actionOnItemSelectedListenerObservable(): Observable<Int> {
        return Observable.create {
            e: ObservableEmitter<Int>? ->
            sp_select!!.onItemSelectedListener = object:
                    AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent:
                                            AdapterView<*>?,
                                            view: View?,
                                            position: Int, id: Long) {
                    e!!.onNext(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    e!!.setCancellable { Cancellable{
                        sp_select!!.onItemSelectedListener = null
                    } }
                }
            }

        }
    }

   /* private fun handleFormsCloud(list: List<Form>?){
        if (list != null && list.isNotEmpty()){
            insertFormsViewModel .list = list
            insertFormsViewModel .insertForms()
        }

    }

    private fun handleInsertForms(value: Boolean?){
        //hideProgress()
        if (value != null && value){
            println("Insert Forms OK")
        }
    }*/

   /* private fun getFormsOfCloud(id: Int){
        //showProgress()

        val url = String.format(Constants.urlBase +
                "${Constants.serviceFormsByProject}${id}")
        val token = "Bearer " + this.prefs.getString(Constants.prefToken, "")
        getFormsCloudViewModel.url = url
        getFormsCloudViewModel.token = token
        if (getFormsCloudViewModel.verifyInput()){
            getFormsCloudViewModel.requestForms()
        }


    }*/
}