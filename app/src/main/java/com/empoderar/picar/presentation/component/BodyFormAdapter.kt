package com.empoderar.picar.presentation.component

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.empoderar.picar.R
import com.empoderar.picar.presentation.data.BodyFormView
import com.empoderar.picar.presentation.data.FormView
import com.empoderar.picar.presentation.extension.inflate
import com.empoderar.picar.presentation.navigation.Navigator
import kotlinx.android.synthetic.main.view_row_body.view.*
import kotlinx.android.synthetic.main.view_row_form.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class BodyFormAdapter @Inject constructor():
        RecyclerView.Adapter<BodyFormAdapter.ViewHolder>() {

    internal var collection: List<BodyFormView> by Delegates.observable(emptyList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    internal var clickListener: (BodyFormView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            BodyFormAdapter.ViewHolder(parent.inflate(R.layout.view_row_body))

    override fun onBindViewHolder(viewHolder: BodyFormAdapter.ViewHolder, position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bodyFormView: BodyFormView, clickListener: (BodyFormView, Navigator.Extras) -> Unit) {
            itemView.lbl_code.text = bodyFormView.code
            itemView.lbl_code.tag = bodyFormView.id
            itemView.lbl_description.text = bodyFormView.description
            itemView.et_value.setText(bodyFormView.value)
            itemView.et_cumple.setText(bodyFormView.satisfy)
            itemView.et_date.setText(bodyFormView.date)
            itemView.et_comment.setText(bodyFormView.comment)
            itemView.setOnClickListener {
                clickListener(bodyFormView,
                        Navigator.Extras(itemView.lbl_code))

            }
        }
    }

}