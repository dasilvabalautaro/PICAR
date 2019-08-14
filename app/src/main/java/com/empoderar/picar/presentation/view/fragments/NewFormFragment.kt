package com.empoderar.picar.presentation.view.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.data.Photo
import com.empoderar.picar.model.persistent.caching.Variables
import com.empoderar.picar.presentation.component.PhotoAdapter
import com.empoderar.picar.presentation.data.FormView
import com.empoderar.picar.presentation.extension.addDecorationRecycler
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.InsertOneFormViewModel
import com.empoderar.picar.presentation.view.activities.MenuActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_list_project.*
import kotlinx.android.synthetic.main.view_new_form.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class NewFormFragment: BaseFragment() {

    @Inject
    lateinit var photoAdapter: PhotoAdapter
    @Inject
    lateinit var navigator: Navigator

    private lateinit var insertOneFormViewModel: InsertOneFormViewModel

    override fun layoutId() = R.layout.view_new_form

    private val listPhoto = ArrayList<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        val image = (activity as MenuActivity)
                .observableImage.map { i -> i }
        disposable.add(image.observeOn(Schedulers.newThread())
                .map { i ->
                    run {
                        return@map Bitmap.createScaledBitmap(i,
                                (i.width*0.9).toInt(),
                                (i.height*0.9).toInt(), true)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { resize ->
                    run {
                        val photo = Photo(resize, Date().toString())
                        listPhoto.add(photo)
                        photoAdapter.collection = listPhoto.toList()
                    }
                })
        insertOneFormViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleInsertForm)
            failure(failure, ::handleFailure)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    private fun handleInsertForm(id: Long?){

    }

    override fun renderFailure(message: Int) {
        notify(message)
    }

    private fun initializeView(){
        rv_photos!!.setHasFixedSize(true)
        rv_photos!!.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL, false)
        addDecorationRecycler(rv_photos, context!!)
        rv_photos.adapter = photoAdapter
        /*photoAdapter.clickListener = { photo, navigationExtras ->
            navigator.showForms(activity!!, photo, navigationExtras) }*/

        tv_date!!.text = Date().toString()
        tv_latitude.text = Variables.locationUser.lat.toString()
        tv_longitude.text = Variables.locationUser.lon.toString()
        ib_photo!!.setOnClickListener { (activity as MenuActivity).camera() }
        ib_save!!.setOnClickListener { insertForm() }
    }

    private fun insertForm(){

        val form = Form(0, proyectView!!.id, "053", 1,
                et_title.text.toString(), "", 1, "",
                123, Variables.locationUser.lat,
                Variables.locationUser.lon, tv_date.text.toString(), "")
        insertOneFormViewModel.form = form
        insertOneFormViewModel.insertForm()
    }
}