package com.empoderar.picar.presentation.view.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.presentation.component.ProjectsAdapter
import com.empoderar.picar.presentation.extension.addDecorationRecycler
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import kotlinx.android.synthetic.main.view_projects.*
import javax.inject.Inject

class ProjectsFragment: BaseFragment() {
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var projectsAdapter: ProjectsAdapter

    override fun layoutId() = R.layout.view_projects
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}