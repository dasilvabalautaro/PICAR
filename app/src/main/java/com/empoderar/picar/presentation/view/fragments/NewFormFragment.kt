package com.empoderar.picar.presentation.view.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.empoderar.picar.R
import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.data.Image
import com.empoderar.picar.domain.data.Photo
import com.empoderar.picar.model.persistent.caching.Variables
import com.empoderar.picar.model.persistent.files.ManageFiles
import com.empoderar.picar.presentation.component.PhotoAdapter
import com.empoderar.picar.presentation.data.FormView
import com.empoderar.picar.presentation.data.ImageView
import com.empoderar.picar.presentation.extension.addDecorationRecycler
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.GetImagesByFormViewModel
import com.empoderar.picar.presentation.presenter.InsertImagesViewModel
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
    @Inject
    lateinit var manageFiles: ManageFiles

    var flagNewForm = true
    var formView: FormView? = null
    private var numberPhotos = 0

    private lateinit var insertOneFormViewModel: InsertOneFormViewModel
    private lateinit var insertImagesViewModel: InsertImagesViewModel
    private lateinit var getImagesByFormViewModel: GetImagesByFormViewModel

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

        insertImagesViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleInsertImages)
            failure(failure, ::handleFailure)
        }

        getImagesByFormViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetImages)
            failure(failure, ::handleFailure)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        fillDataControl()
    }

    private fun handleInsertForm(id: Long?){

        if (id != null){
            val list = ArrayList<Image>()
            for (i in this.numberPhotos until this.listPhoto.count()){
                val base64 = manageFiles
                        .base641EncodedImage(this.listPhoto[i].image!!)
                val img = Image(0, id.toInt(), base64, this.listPhoto[i].date)
                list.add(img)

            }
            if (!list.isNullOrEmpty()){
                insertImagesViewModel.list = list.toList()
                insertImagesViewModel.insertImages()
            }
        }
    }

    private fun handleInsertImages(value: Boolean?){
        if (value != null && value){
            context!!.toast("Insert OK")
        }
    }

    private fun handleGetImages(list: List<ImageView>?){
        if (!list.isNullOrEmpty()){
            for (imv: ImageView in list){
                val bitmap = manageFiles.base64DecodeImage(imv.base64)
                val photo = Photo(bitmap, imv.date)
                this.listPhoto.add(photo)
            }
            this.numberPhotos = this.listPhoto.count()
            photoAdapter.collection = listPhoto.toList()
        }
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

        ib_photo!!.setOnClickListener { (activity as MenuActivity).camera() }
        ib_save!!.setOnClickListener { insertForm() }
    }

    private fun setNewDataControl(){
        tv_date!!.text = Date().toString()
        tv_latitude.text = Variables.locationUser.lat.toString()
        tv_longitude.text = Variables.locationUser.lon.toString()

    }

    private fun setUpdateDataControl(){
        tv_date!!.text = formView!!.dateCreation
        tv_latitude.text = formView!!.latitude.toString()
        tv_longitude.text = formView!!.longitude.toString()
        et_title!!.setText(formView!!.title)
        tv_title!!.text = resources.getString(R.string.lbl_update_form)
        getImagesByFormViewModel.idForm = formView!!.id
        getImagesByFormViewModel.loadImages()
    }

    private fun fillDataControl(){
        if (this.flagNewForm){
            setNewDataControl()
        }else{
            setUpdateDataControl()
        }
    }

    private fun insertForm(){
        if (flagNewForm){
            insertNewForm()
        }else{
            updateForm()
        }

    }

    private fun insertNewForm(){
        val form = Form(0, proyectView!!.id, "053", 1,
                et_title.text.toString(), "", 1, "",
                123, Variables.locationUser.lat,
                Variables.locationUser.lon, tv_date.text.toString(), "")
        insertOneFormViewModel.form = form
        insertOneFormViewModel.insertForm()

    }

    private fun updateForm(){
        val form = Form(formView!!.id, formView!!.project, formView!!.frmId,
                formView!!.frmNro, et_title.text.toString(), "",
                formView!!.state, "", formView!!.userMobile,
                formView!!.latitude, formView!!.longitude, formView!!.dateCreation,
                "")
        insertOneFormViewModel.form = form
        insertOneFormViewModel.insertForm()

    }
}
