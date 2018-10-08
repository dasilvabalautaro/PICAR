package com.empoderar.picar.presentation.component

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.empoderar.picar.R
import com.empoderar.picar.presentation.data.ProjectView
import com.empoderar.picar.presentation.extension.inflate
import com.empoderar.picar.presentation.navigation.Navigator
import kotlinx.android.synthetic.main.view_row_project.view.*
import javax.inject.Inject
import kotlin.properties.Delegates


class ProjectsAdapter @Inject constructor():
        RecyclerView.Adapter<ProjectsAdapter.ViewHolder>() {

    internal var collection: List<ProjectView> by Delegates.observable(emptyList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    internal var clickListener: (ProjectView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.view_row_project))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(projectView: ProjectView, clickListener: (ProjectView, Navigator.Extras) -> Unit) {
            itemView.tv_title.text = projectView.name
            itemView.tv_title.tag = projectView.id
            itemView.setOnClickListener {
                clickListener(projectView,
                        Navigator.Extras(itemView.tv_title))

            }
        }
    }
}