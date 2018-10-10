package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.presentation.component.ProjectsAdapter
import com.empoderar.picar.presentation.data.ProjectView
import com.empoderar.picar.presentation.extension.addDecorationRecycler
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.GetProjectsViewModel
import kotlinx.android.synthetic.main.view_list_project.*
import javax.inject.Inject

class ProjectsFragment: BaseFragment() {
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var projectsAdapter: ProjectsAdapter

    private lateinit var getProjectsViewModel: GetProjectsViewModel

    override fun layoutId() = R.layout.view_list_project
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        getProjectsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetProjects)
            failure(failure, ::handleFailure)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        setupSwipeRefresh()
        loadProjectList()
    }

    private fun loadProjectList(){
        getProjectsViewModel.loadProjects()
        sr_projects!!.isRefreshing = false
    }

    private fun handleGetProjects(list: List<ProjectView>?){
        projectsAdapter.collection = list.orEmpty()

    }

    private fun initializeView(){
        rv_projects!!.setHasFixedSize(true)
        rv_projects!!.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL, false)
        addDecorationRecycler(rv_projects, context!!)
        rv_projects.adapter = projectsAdapter
       /* projectsAdapter.clickListener = { project, navigationExtras ->
            navigator.showMovieDetails(activity!!, project, navigationExtras) }*/
    }

    override fun renderFailure(message: Int) {
        notify(message)
    }

    private fun setupSwipeRefresh() = sr_projects!!.setOnRefreshListener(
            this::loadProjectList)
}