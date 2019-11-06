package com.empoderar.picar.presentation.component

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.empoderar.picar.R
import com.empoderar.picar.presentation.data.BodyFormView
import com.empoderar.picar.presentation.extension.inflate
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.view.fragments.BodiesFormFragment
import kotlinx.android.synthetic.main.view_row_body.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class BodyFormAdapter @Inject constructor():
        RecyclerView.Adapter<BodyFormAdapter.ViewHolder>() {

    internal var collection: List<BodyFormView> by Delegates.observable(emptyList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    internal var clickListener: (BodyFormView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.view_row_body))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bodyFormView: BodyFormView, clickListener: (BodyFormView, Navigator.Extras) -> Unit) {
            itemView.lbl_code.text = bodyFormView.code
            itemView.lbl_code.tag = bodyFormView.id
            itemView.lbl_description.text = bodyFormView.description
            itemView.et_value.setText(bodyFormView.value)
            if (bodyFormView.satisfy == "SI"){
                itemView.sp_cumple.setSelection(0)
            }else{
                itemView.sp_cumple.setSelection(1)
            }
            //itemView.et_cumple.setText(bodyFormView.satisfy)
            itemView.et_date.setText(bodyFormView.date)
            itemView.et_comment.setText(bodyFormView.comment)
            itemView.setOnClickListener {
                clickListener(bodyFormView,
                        Navigator.Extras(itemView.lbl_code))

            }

            itemView.et_value.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    BodiesFormFragment.updateValue(s.toString(),
                            (itemView.lbl_code.tag as Int))
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int,
                                           before: Int, count: Int) {

                }

            })
            itemView.sp_cumple.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    val selectedItem = parent.getItemAtPosition(position).toString()

                    BodiesFormFragment.updateSatisfy(selectedItem,
                            (itemView.lbl_code.tag as Int))
                } // to close the onItemSelected

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

           /* itemView.et_cumple.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    BodiesFormFragment.updateSatisfy(s.toString(),
                            (itemView.lbl_code.tag as Int))
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int,
                                           before: Int, count: Int) {

                }

            })*/

            itemView.et_date.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    BodiesFormFragment.updateDate(s.toString(),
                            (itemView.lbl_code.tag as Int))
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int,
                                           before: Int, count: Int) {

                }

            })

            itemView.et_comment.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    BodiesFormFragment.updateComment(s.toString(),
                            (itemView.lbl_code.tag as Int))
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int,
                                           before: Int, count: Int) {

                }

            })
        }


    }

}