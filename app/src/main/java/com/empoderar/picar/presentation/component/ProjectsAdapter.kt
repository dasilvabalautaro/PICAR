package com.empoderar.picar.presentation.component

//import androidx.appcompat.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            itemView.tv_id.text = projectView.id.toString()
            itemView.tv_unity.text = projectView.unity.toString()
            itemView.tv_code_project.text = projectView.codeProject
            itemView.tv_name_project.text = projectView.nameProject
            itemView.tv_id.tag = projectView.id
            itemView.tv_latitude.text = projectView.latitude.toString()
            itemView.tv_longitude.text = projectView.longitude.toString()
            itemView.tv_state.text = projectView.state
            itemView.tv_date_presentation.text = projectView.datePresentation
            itemView.tv_date_init_agreement.text = projectView.dateInitAgreement
            itemView.tv_date_end_agreement.text = projectView.dateEndAgreement
            itemView.setOnClickListener {
                clickListener(projectView,
                        Navigator.Extras(itemView.tv_id))

            }
        }
    }
}