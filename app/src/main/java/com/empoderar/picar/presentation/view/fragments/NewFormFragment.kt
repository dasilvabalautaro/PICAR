package com.empoderar.picar.presentation.view.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.empoderar.picar.R
import com.empoderar.picar.domain.data.BodyForm
import com.empoderar.picar.domain.data.Form
import com.empoderar.picar.domain.data.Image
import com.empoderar.picar.domain.data.Photo
import com.empoderar.picar.model.persistent.caching.Variables
import com.empoderar.picar.model.persistent.files.ManageFiles
import com.empoderar.picar.presentation.component.PhotoAdapter
import com.empoderar.picar.presentation.data.ContentFormView
import com.empoderar.picar.presentation.data.FormView
import com.empoderar.picar.presentation.data.ImageView
import com.empoderar.picar.presentation.data.TypeFormView
import com.empoderar.picar.presentation.extension.*
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.*
import com.empoderar.picar.presentation.view.activities.MenuActivity
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Cancellable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_new_form.*
import kotlinx.android.synthetic.main.view_new_form.sp_select
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
    private var idNewForm = 0
    private var numberPhotos = 0
    private var codesTypesForms: ArrayList<String> = ArrayList()
    private var listTypeForm: List<TypeFormView>? = null
    private var codeTypeForm = String.empty()
    private var listContent: List<ContentFormView>? = null
    private lateinit var insertOneFormViewModel: InsertOneFormViewModel
    private lateinit var insertImagesViewModel: InsertImagesViewModel
    private lateinit var getImagesByFormViewModel: GetImagesByFormViewModel
    private lateinit var getTypesFormViewModel: GetTypesFormViewModel
    private lateinit var getContentFormsViewModel: GetContentFormsViewModel
    private lateinit var insertBodiesFormViewModel: InsertBodiesFormViewModel


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

        getTypesFormViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetTypesForm)
            failure(failure, ::handleFailure)
        }

        getContentFormsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleGetContentForm)
            failure(failure, ::handleFailure)
        }

        insertBodiesFormViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleInsertBodies)
            failure(failure, ::handleFailure)
        }

        getTypesFormViewModel.loadTypesForm()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        fillDataControl()
    }

    override fun onStart() {
        super.onStart()
        disposable.add( actionOnItemSelectedListenerObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .map { position ->
                    run{

                        this.codeTypeForm = sp_select!!
                                .getItemAtPosition(position) as String
                        getContentFormsViewModel.frmId = this.codeTypeForm
                        getContentFormsViewModel.loadContents()
                        return@map resources.getString(R.string.new_filter)
                    }
                }
                .subscribe { result -> println(result)})
    }

    private fun handleInsertBodies(value: Boolean?){
        if (value != null && value){
            context!!.toast("Insert Bodies OK")
        }
    }

    private fun handleGetContentForm(list: List<ContentFormView>?){
        this.listContent = list.orEmpty()
    }

    private fun insertBodies(){
        val list = ArrayList<BodyForm>()

        for (content:ContentFormView in this.listContent!!){
            val body = BodyForm(0, this.idNewForm, content.varCodigo,
                    "", content.description, "",
                    "", "")
            list.add(body)
        }
        insertBodiesFormViewModel.list = list.toList()
        insertBodiesFormViewModel.insertBodiesForm()
    }
    private fun handleGetTypesForm(list: List<TypeFormView>?){
        this.listTypeForm = list.orEmpty()
        loadCodesTypes()
    }

    private fun loadCodesTypes(){
        if (this.listTypeForm != null && this.listTypeForm!!.isNotEmpty()){
            loadValueCodes(this.listTypeForm!!)
            setDataSpinner()
        }

    }

    private fun loadValueCodes(list: List<TypeFormView>){
        for (type: TypeFormView in list){
            this.codesTypesForms.add(type.frmId)
        }
    }

    private fun setDataSpinner(){

        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(context!!,
                android.R.layout.simple_list_item_1, this.codesTypesForms)
        sp_select!!.adapter = spinnerAdapter
        spinnerAdapter.notifyDataSetChanged()
        if (sp_select!!.adapter != null){
            sp_select!!.refreshDrawableState()
        }

    }

    private fun getPositionInSpinner(value: String): Int{
        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(context!!,
                android.R.layout.simple_list_item_1, this.codesTypesForms)
        return spinnerAdapter.getPosition(value)
    }

    private fun handleInsertForm(id: Long?){

        if (id != null){
            this.idNewForm = id.toInt()
            val list = ArrayList<Image>()
            for (i in this.numberPhotos until this.listPhoto.count()){
                val base64 = manageFiles
                        .base641EncodedImage(this.listPhoto[i].image!!)
                val img = Image(0, id.toInt(), base64, this.listPhoto[i].date)
                list.add(img)

            }
            insertBodies()
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
        ib_bodies!!.setOnClickListener {
            navigator.showBodies(activity!!, this.idNewForm)
        }
    }

    private fun setNewDataControl(){
        tv_date!!.text = Date().toString()
        tv_latitude.text = Variables.locationUser.lat.toString()
        tv_longitude.text = Variables.locationUser.lon.toString()

    }

    private fun setUpdateDataControl(){
        this.idNewForm = formView!!.id
        tv_date!!.text = formView!!.dateCreation
        tv_latitude.text = formView!!.latitude.toString()
        tv_longitude.text = formView!!.longitude.toString()
        et_title!!.setText(formView!!.title)
        tv_title!!.text = resources.getString(R.string.lbl_update_form)
        getImagesByFormViewModel.idForm = formView!!.id
        getImagesByFormViewModel.loadImages()
        sp_select!!.setSelection(getPositionInSpinner(formView!!.frmId))
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
        val form = Form(0, proyectView!!.id, this.codeTypeForm , 1,
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

    private fun actionOnItemSelectedListenerObservable(): Observable<Int> {
        return Observable.create {
            e: ObservableEmitter<Int>? ->
            sp_select!!.onItemSelectedListener = object:
                    AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent:
                                            AdapterView<*>?,
                                            view: View?,
                                            position: Int, id: Long) {
                    e!!.onNext(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    e!!.setCancellable { Cancellable{
                        sp_select!!.onItemSelectedListener = null
                    } }
                }
            }

        }
    }

}
