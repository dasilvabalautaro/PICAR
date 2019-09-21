package com.empoderar.picar.presentation.view.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.empoderar.picar.App
import com.empoderar.picar.R
import com.empoderar.picar.di.ApplicationComponent
import com.empoderar.picar.model.observer.LocationChangeObserver
import com.empoderar.picar.model.persistent.caching.Variables
import com.empoderar.picar.model.persistent.files.ManageFiles
import com.empoderar.picar.model.persistent.network.ManagerLocation
import com.empoderar.picar.presentation.component.ExpandableListMenu
import com.empoderar.picar.presentation.component.MenuExpandableAdapter
import com.empoderar.picar.presentation.plataform.BaseActivity
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.android.synthetic.main.toolbar.*
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.tools.Validate
import com.empoderar.picar.presentation.view.fragments.*
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MenuActivity : BaseActivity(){

    companion object {
        fun callingIntent(context: Context) = Intent(context,
                MenuActivity::class.java)
    }

    @Inject
    lateinit var manageFiles: ManageFiles
    @Inject
    internal lateinit var navigator: Navigator
    @Inject
    lateinit var managerLocation: ManagerLocation
    @Inject
    lateinit var locationChangeObserver: LocationChangeObserver

    private var menuExpandableListAdapter: MenuExpandableAdapter? = null
    private var expandableHeaderMenu: List<String>? = null
    private var expandableListMenu: MutableMap<String, List<String>>? = null
    private var toggle: ActionBarDrawerToggle? = null
    private lateinit var mainMenu: Menu
    var image: Bitmap? = null
    var observableImage: Subject<Bitmap> = PublishSubject.create()

    override fun fragment() = ListingsFragment()

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        enablePermissions.permissionServiceLocation(this)
        enablePermissions.permissionCamera(this)
        if (networkHandler.isConnected == null || !networkHandler.isConnected!!){
            Toast.makeText(this,
                    getString(R.string.failure_network_connection),
                    Toast.LENGTH_SHORT).show()

        }
        if (!managerLocation.isJobServiceOn()){
            if (!managerLocation.start()){
                println("Service Location not run.")
            }
        }

        val hearLocation = locationChangeObserver.observableLocation.map { l -> l }
        disposable.add(hearLocation.observeOn(Schedulers.newThread())
                .subscribe { l ->
                    run {
                        Variables.locationUser.lat= l.latitude
                        Variables.locationUser.lon = l.longitude


                    }
                })
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        navList.visibility = View.VISIBLE
        toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle!!)
        toggle!!.syncState()
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
            val selectedItem = this.expandableListMenu!![this
                    .expandableHeaderMenu!![groupPosition]]!![childPosition]
            executeOptionMenu(groupPosition, childPosition)

            drawer_layout.closeDrawer(GravityCompat.START)

            return@setOnChildClickListener false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == enablePermissions.accessReadExternal &&
                resultCode == Activity.RESULT_OK && data != null) {
            this.image = manageFiles.getBitmap(data.data)
            if (this.image != null){
                this.observableImage.onNext(this.image!!)
            }

        } else if (requestCode == enablePermissions.accessCamera &&
                resultCode == Activity.RESULT_OK) {

            val photoUri = FileProvider.getUriForFile(this,
                    applicationContext.packageName +
                            ".provider", manageFiles
                    .getCameraFile())
            this.image = manageFiles.getBitmap(photoUri)
            if (this.image != null){
                this.observableImage.onNext(this.image!!)
            }

        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.action_new_form).isVisible = false
        mainMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        if (id == R.id.action_new_form){
            /*if (BaseFragment.proyectView != null){
                val newForm = NewFormFragment()
                newForm.flagNewForm = true
                addFragment(newForm)
            }else{
                Toast.makeText(this,
                        getString(R.string.failure_project_not_selected),
                        Toast.LENGTH_SHORT).show()
            }*/
        }
        if (toggle!!.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    private fun executeOptionMenu(groupPosition: Int, childPosition: Int){
        setOrientation(true)
        when(groupPosition){
            0 -> {
                when(childPosition){
                    0 -> {
                        val listFragment = ListingsFragment()
                        addFragment(listFragment )
                    }

                    1 -> {

                    }

                }
            }
            1 -> {
                when(childPosition){
                    0 -> {
                        val mapFragment = MapFragment()
                        addFragment(mapFragment)
                    }

                    1 -> {
                        val mapProjectsFragment = MapProjectsFragment()
                        addFragment(mapProjectsFragment)
                    }

                }
            }
            2 -> {
                when(childPosition){
                    0 -> {
                        val uploadFragment = UploadFragment()
                        addFragment(uploadFragment)
                    }

                    1 -> {

                    }

                }

            }
        }
    }

    override fun onDestroy() {
        managerLocation.cancel()
        disposable.dispose()
        println("DESTROY OBJECTS")
        super.onDestroy()
    }

    fun setMenuAddForm(option: Boolean){
        mainMenu.findItem(R.id.action_new_form).isVisible = option

    }

    fun setOrientation(option: Boolean){
        requestedOrientation = if (option){
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }else{
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    fun gallery(){
        enablePermissions.startGalleryChooser(this)
    }

    fun camera(){

        enablePermissions.startCamera(this)
    }

}
