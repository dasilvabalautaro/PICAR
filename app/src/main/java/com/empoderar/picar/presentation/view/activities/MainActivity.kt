package com.empoderar.picar.presentation.view.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import com.empoderar.picar.R
import com.empoderar.picar.presentation.component.ExpandableListMenu
import com.empoderar.picar.presentation.component.MenuExpandableAdapter
import com.empoderar.picar.presentation.plataform.BaseActivity
import com.empoderar.picar.presentation.view.fragments.ProjectsFragment
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.android.synthetic.main.toolbar.*
import android.widget.TextView

class MainActivity : BaseActivity(){

    companion object {
        fun callingIntent(context: Context) = Intent(context,
                MainActivity::class.java)
    }

    private var menuExpandableListAdapter: MenuExpandableAdapter? = null
    private var expandableHeaderMenu: List<String>? = null
    private var expandableListMenu: MutableMap<String, List<String>>? = null
    override fun fragment() = ProjectsFragment()

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navList.addHeaderView(layoutInflater.inflate(R.layout.nav_header_main,
                null, false))
        this.expandableListMenu = ExpandableListMenu().getListMenu(this)
        this.expandableHeaderMenu = this.expandableListMenu!!.keys.toList()
        addDrawerOptions()

    }

    private fun addDrawerOptions(){
        menuExpandableListAdapter = MenuExpandableAdapter(this,
                navList, this.expandableHeaderMenu!!, this.expandableListMenu!!)
        navList.setAdapter(menuExpandableListAdapter)
        navList.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            // TODO Auto-generated method stub

            val cp = menuExpandableListAdapter!!
                    .getChildId(groupPosition, childPosition)

            val op = (v as TextView).text.toString()

            if (cp == 3L) {
                when (cp) {
                    3L -> {
                    }
                }
            }
            drawer_layout.closeDrawer(GravityCompat.START)

            false
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }




}
