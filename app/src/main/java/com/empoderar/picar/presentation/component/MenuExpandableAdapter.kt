package com.empoderar.picar.presentation.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import com.empoderar.picar.R

class MenuExpandableAdapter(private var context: Context,
                            private var expandableListView: ExpandableListView,
                            private var header: List<String>,
                            private var body: MutableMap<String, List<String>>):
        BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): String {
        return header[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    @SuppressLint("InflateParams")
    override fun getGroupView(groupPosition: Int,
                              isExpanded: Boolean, convertView: View?,
                              parent: ViewGroup?): View? {
        var convertViewLocal = convertView

        if (convertView == null){
            val inflater = context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertViewLocal = inflater.inflate(R.layout.view_group_menu, null)
        }

        val title = convertViewLocal?.findViewById<TextView>(R.id.tv_title_menu)
        title?.text = getGroup(groupPosition)
        title?.typeface = Typeface.DEFAULT_BOLD
        title?.setOnClickListener {
            if (expandableListView.isGroupExpanded(groupPosition)){
                expandableListView.collapseGroup(groupPosition)
            }else{
                expandableListView.expandGroup(groupPosition)
            }
        }
        return convertViewLocal
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return body[header[groupPosition]]!!.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this.body[header[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getChildView(groupPosition: Int, childPosition: Int,
                              isLastChild: Boolean, convertView: View?,
                              parent: ViewGroup?): View? {
        var convertViewLocal = convertView

        if (convertView == null){
            val inflater = context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertViewLocal = inflater.inflate(R.layout.view_item_menu, null)
        }

        val title = convertViewLocal?.findViewById<TextView>(R.id.tv_expanded_item)
        title?.text = getChild(groupPosition, childPosition) as String
        return convertViewLocal
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return header.size
    }

}