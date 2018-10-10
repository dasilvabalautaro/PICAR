package com.empoderar.picar.presentation.component

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.empoderar.picar.R
import com.empoderar.picar.presentation.data.FormView
import com.empoderar.picar.presentation.extension.inflate
import com.empoderar.picar.presentation.navigation.Navigator
import kotlinx.android.synthetic.main.view_row_project.view.*
import javax.inject.Inject
import kotlin.properties.Delegates


class FormsAdapter @Inject constructor():
        RecyclerView.Adapter<FormsAdapter.ViewHolder>()  {

    internal var collection: List<FormView> by Delegates.observable(emptyList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    internal var clickListener: (FormView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            FormsAdapter.ViewHolder(parent.inflate(R.layout.view_row_form))

    override fun onBindViewHolder(viewHolder: FormsAdapter.ViewHolder, position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(formView: FormView, clickListener: (FormView, Navigator.Extras) -> Unit) {
            itemView.tv_title.text = formView.comment1
            itemView.tv_title.tag = formView.id
            itemView.setOnClickListener {
                clickListener(formView,
                        Navigator.Extras(itemView.tv_title))

            }
        }
    }
}