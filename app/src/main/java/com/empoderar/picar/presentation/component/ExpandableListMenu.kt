package com.empoderar.picar.presentation.component

import android.content.Context
import com.empoderar.picar.R

class ExpandableListMenu {

    fun getListMenu(context: Context): MutableMap<String,List<String>>{
        val map:MutableMap<String, List<String>> = mutableMapOf()

        val options = (context.resources
                .getStringArray(R.array.options).asList())
        val project = (context.resources
                .getStringArray(R.array.project).asList())
        val maps = (context.resources
                .getStringArray(R.array.maps).asList())
        val system = (context.resources
                .getStringArray(R.array.system).asList())
        val help = (context.resources
                .getStringArray(R.array.help).asList())
        val graphics = (context.resources
                .getStringArray(R.array.graphics).asList())

        map[options[0]] = project
        map[options[1]] = maps
        map[options[2]] = system
        map[options[3]] = graphics
        map[options[4]] = help
        return map
    }
}