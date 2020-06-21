package com.empoderar.picar.presentation.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.empoderar.picar.R
import com.empoderar.picar.domain.data.Unity
import com.empoderar.picar.model.persistent.caching.Constants
import com.empoderar.picar.model.persistent.preference.PreferenceRepository
import com.empoderar.picar.presentation.component.ProjectsAdapter
import com.empoderar.picar.presentation.data.ProjectView
import com.empoderar.picar.presentation.extension.addDecorationRecycler
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.*
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Cancellable
import kotlinx.android.synthetic.main.view_list_project.*
import javax.inject.Inject

class ProjectsFragment: BaseFragment() {
    companion object{
        @JvmStatic
        fun newInstance()=ProjectsFragment()
    }

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var projectsAdapter: ProjectsAdapter

    private lateinit var getUnitsViewModel: GetUnitsDatabaseViewModel
    private var namesUnits: ArrayList<String> = ArrayList()
    private lateinit var getProjectsViewModel: GetProjectsDatabaseViewModel
    private var idUnityTemp = -1
    private var positionSpinnerCurrent = 0
    private lateinit var prefs: SharedPreferences


    override fun layoutId() = R.layout.view_list_project
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProjectsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetProjects)
            failure(failure, ::handleFailure)
        }
        getUnitsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetUnits)
            failure(failure, ::handleFailure)
        }

        this.prefs = PreferenceRepository.customPrefs(requireActivity(),
                Constants.preference_picar)
        loadUnitsDatabase()
        loadProjectList()
        initializeView()
        setupSwipeRefresh()
        setListProjectForUnity()
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
                        this.positionSpinnerCurrent = position
                        setListProjectForUnity()

                        return@map resources.getString(R.string.new_filter)
                    }
                }
                .subscribe { result -> println(result)})

    }


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
        addDecorationRecycler(rv_projects, requireContext())
        rv_projects.adapter = projectsAdapter
        projectsAdapter.clickListener = { project, navigationExtras ->
            navigator.showForms(requireActivity(), project, navigationExtras) }

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
        when(val unityId = this.prefs.getInt(Constants.prefUnity, -1)){
            0 -> {
                for (u: Unity in list){
                    this.namesUnits.add(u.name)

                }

            }
            -1 -> {
                requireContext().toast(getString(R.string.msg_error_wrong_unit))
            }
            else -> {
                val unity = list.first { u -> u.id == unityId }
                this.namesUnits.add(unity.name)
            }
        }
    }

    private fun setDataSpinner(){
        if (this.namesUnits.count() > 0){
            val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(),
                    android.R.layout.simple_list_item_1, this.namesUnits)
            sp_select!!.adapter = spinnerAdapter
            spinnerAdapter.notifyDataSetChanged()
            sp_select!!.setSelection(this.positionSpinnerCurrent)

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


}