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
import com.empoderar.picar.model.persistent.caching.Variables
import com.empoderar.picar.model.persistent.files.ManageFiles
import com.empoderar.picar.presentation.component.PhotoAdapter
import com.empoderar.picar.presentation.data.*
import com.empoderar.picar.presentation.extension.*
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.permission.EnablePermissions
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.presenter.*
import com.empoderar.picar.presentation.tools.Transform
import com.empoderar.picar.presentation.view.activities.MenuActivity
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Cancellable
import kotlinx.android.synthetic.main.view_new_form.*
import kotlinx.android.synthetic.main.view_new_form.sp_select

import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class NewFormFragment: BaseFragment() {

    companion object{
        var flagNewForm = true
        var formView: FormView? = null

        @JvmStatic
        fun newInstance()= NewFormFragment()
    }

    @Inject
    lateinit var photoAdapter: PhotoAdapter
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var manageFiles: ManageFiles

    private var idNewForm = 0
    private var numberPhotos = 0
    private var fragmentIsCreated = false
    private var codesTypesForms: ArrayList<String> = ArrayList()
    private var listTypeForm: List<TypeFormView>? = null
    private var codeTypeForm = String.empty()
    private var listContent = ArrayList<ContentFormView>()
    private lateinit var insertOneFormViewModel: InsertOneFormViewModel
    private lateinit var insertImagesViewModel: InsertImagesViewModel
    private lateinit var getImagesByFormViewModel: GetImagesByFormViewModel
    private lateinit var getTypesFormViewModel: GetTypesFormViewModel
    private lateinit var getContentFormsViewModel: GetContentFormsViewModel
    private lateinit var insertBodiesFormViewModel: InsertBodiesFormViewModel
    private lateinit var updateUploadFormsViewModel: UpdateUploadFormsViewModel
    @Inject
    lateinit var enablePermissions: EnablePermissions

  /*  private val keyTitle = "keyTitle"
    private val keyCodeTypeForm = "keyCodeTypeForm"
    private val keyListPhotoView = "keyListPhotoView"
    private val keyLatitude = "keyLatitude"
    private val keyLongitude = "keyLongitude"
    private val keyIdNewForm = "keyIdNewForm"
    private val keyListContent = "keyListContent"
    private val keyDate = "keyDate"*/

    override fun layoutId() = R.layout.view_new_form

    private var listPhoto = ArrayList<PhotoView>()

    private var disposableImage: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = (activity as MenuActivity)
                .observableImage.map { i -> i }
        disposableImage = image.observeOn(AndroidSchedulers.mainThread())
                .map { i ->
                    kotlin.run {
                        return@map Bitmap.createScaledBitmap(i,
                                (i.width*0.9).toInt(),
                                (i.height*0.9).toInt(), true)
                    }
                }
                .subscribe { resize ->
                    kotlin.run {
                        val dateLong = System.currentTimeMillis()
                        // val dateToCloud = String.format(Locale.US,"/Date(%d)/", dateLong)
                        val dateToCloud = dateLong.toString()
                        val photo = PhotoView(resize, dateToCloud,
                                Variables.locationUser.lat,
                                Variables.locationUser.lon)
                        listPhoto.add(photo)
                        photoAdapter.collection = listPhoto.toList()

                    }
                } //.observeOn(AndroidSchedulers.mainThread()) // Schedulers.newThread()


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

        updateUploadFormsViewModel = viewModel(viewModelFactory) {
            observe(result, ::handleUpdateForm)
            failure(failure, ::handleFailure)
        }
        initializeView()
        getTypesFormViewModel.loadTypesForm()

        this.fragmentIsCreated = true
        //fillDataControl()
        //loadCodesTypes()
    }


    override fun onStart() {
        super.onStart()
        disposable.add( actionOnItemSelectedListenerObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .map { position ->
                    run{

                        this.codeTypeForm = sp_select!!
                                .getItemAtPosition(position) as String
                        if (this.codeTypeForm.isEmpty().not()){
                            getContentFormsViewModel.frmId = this.codeTypeForm
                            getContentFormsViewModel.loadContents()

                        }
                        return@map resources.getString(R.string.new_filter)
                    }
                }
                .subscribe { result -> println(result)})
        if (this.fragmentIsCreated){
            this.fragmentIsCreated = false
            et_title!!.setText("")
        }

    }

    /*override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && this.fragmentIsCreated){
            fillDataControl()
            loadCodesTypes()
        }
    }*/

  /*  override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(keyTitle, et_title.text.toString())
        outState.putString(keyDate, tv_date.text.toString())
        outState.putString(keyLatitude, tv_latitude.text.toString())
        outState.putString(keyLongitude, tv_longitude.text.toString())
        outState.putString(keyCodeTypeForm, this.codeTypeForm)
        outState.putInt(keyIdNewForm, this.idNewForm)
        outState.putParcelableArrayList(keyListPhotoView, this.listPhoto)
        outState.putParcelableArrayList(keyListContent, this.listContent)

    }
*/
 /*   override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null ){
            if (!isNewProject){
                et_title.setText(savedInstanceState.getString(keyTitle))
                tv_latitude.text = savedInstanceState.getString(keyLatitude)
                tv_longitude.text = savedInstanceState.getString(keyLongitude)
                tv_date.text = savedInstanceState.getString(keyDate)
                this.codeTypeForm = savedInstanceState.getString(keyCodeTypeForm)!!

                this.listPhoto = savedInstanceState
                        .getParcelableArrayList(keyListPhotoView)!!
                this.listContent = savedInstanceState.getParcelableArrayList(keyListContent)!!
            }else{
                savedInstanceState.clear()
                isNewProject = false

            }
        }else{
            isNewProject = false
        }
    }*/

    override fun onResume() {
        super.onResume()
        if (proyectView == null){
            context!!.toast(getString(R.string.failure_project_not_selected))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        BodiesFormFragment.formId = 0
        disposableImage!!.dispose()
    }

    private fun handleUpdateForm(value: Boolean?){
        if (value != null && value){
            context!!.toast("Update Form OK")
        }
    }

    private fun handleInsertBodies(value: Boolean?){
        if (value != null && value){
            ib_bodies.visibility = View.VISIBLE
            cb_finished!!.isEnabled = true
            ib_save!!.visibility = View.INVISIBLE
            context!!.toast("Insert Bodies OK")
        }
    }

    private fun handleGetContentForm(list: List<ContentFormView>?){
        this.listContent = ArrayList(list.orEmpty())
    }

    private fun insertBodies(){
        val list = ArrayList<BodyForm>()
        val dateLong = System.currentTimeMillis()
        //val dateToCloud = String.format(Locale.US,"/Date(%d)/", dateLong)
        val dateToCloud = dateLong.toString()
        for (content:ContentFormView in this.listContent){
            val body = BodyForm(0, this.idNewForm, proyectView!!.id,
                    content.varCodigo, "12", content.description, "SI",
                    dateToCloud, "None")
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
            initDataSpinner()

        }

    }

    private fun initDataSpinner(){

        if (this.codeTypeForm.isNotEmpty()){
            setSpinnerTypeForm(this.codeTypeForm)

        }
    }


    private fun loadValueCodes(list: List<TypeFormView>){
        this.codesTypesForms.clear()
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

    private fun setSpinnerTypeForm(frmId: String){
        if (frmId.isNotEmpty()){
            for (i in 0 until sp_select!!.adapter.count){
                val value = sp_select!!.adapter.getItem(i) as String
                if (value == frmId){
                    sp_select!!.setSelection(i)
                    break
                }
            }
        }

    }

    private fun handleInsertForm(id: Long?){

        if (id != null){
            this.idNewForm = id.toInt()
            val list = ArrayList<Image>()
            for (i in this.numberPhotos until this.listPhoto.count()){
                val base64 = manageFiles
                        .base641EncodedImage(this.listPhoto[i].image)
                val img = Image(0, id.toInt(), proyectView!!.id,
                        base64, this.listPhoto[i].latitude,
                        this.listPhoto[i].longitude, this.listPhoto[i].date)
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
                val photo = PhotoView(bitmap, imv.date, imv.latitude, imv.longitude)
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
        ib_bodies.visibility = View.INVISIBLE
        ib_photo!!.setOnClickListener { (activity as MenuActivity).camera() }
        ib_save!!.setOnClickListener { insertForm() }
        ib_bodies!!.setOnClickListener {
            navigator.showBodies(activity!!, this.idNewForm)
        }
        ib_add!!.setOnClickListener { setNewDataControl() }
        cb_finished!!.setOnClickListener {
            if (cb_finished!!.isChecked) {
                updateUploadForm(1)
            } else {
                updateUploadForm(0)
            }
        }
        this.codeTypeForm = String.empty()
        this.idNewForm = 0

    }

    private fun updateUploadForm(upload: Int){
        updateUploadFormsViewModel.idForm = this.idNewForm
        updateUploadFormsViewModel.value = upload
        updateUploadFormsViewModel.buildParams()
        updateUploadFormsViewModel.updateUploadForms()

    }

    private fun setNewDataControl(){
        flagNewForm = true
        formView = null
        BodiesFormFragment.formId = 0
        this.codeTypeForm = String.empty()
        tv_title!!.text = getString(R.string.lbl_new_form)
        tv_date!!.text = Date().toString()
        et_title!!.setText("")
        ib_bodies.visibility = View.INVISIBLE
        ib_save!!.visibility = View.VISIBLE
        tv_latitude.text = Variables.locationUser.lat.toString()
        tv_longitude.text = Variables.locationUser.lon.toString()
        cb_finished!!.isEnabled = false
        sp_select!!.isEnabled = true
        sp_select!!.setSelection(0)
        this.listPhoto.clear()
        photoAdapter.collection = this.listPhoto.toList()
        /*if (itemView != 0){
            firstVisibleItem = (rvOts!!
                    .layoutManager as LinearLayoutManager)
                    .findFirstVisibleItemPosition()
        }*/
        rv_photos!!.refreshDrawableState()
        rv_photos!!.scrollToPosition(rv_photos!!.adapter!!.itemCount)
    }

    fun setUpdateDataControl(){
        this.idNewForm = formView!!.id
        this.codeTypeForm = formView!!.frmId
        BodiesFormFragment.formId = this.idNewForm
        tv_date!!.text = Transform.convertLongToTime(formView!!.dateCreation.toLong())
        tv_latitude.text = formView!!.latitude.toString()
        tv_longitude.text = formView!!.longitude.toString()
        et_title!!.setText(formView!!.title)
        tv_title!!.text = resources.getString(R.string.lbl_update_form)
        getImagesByFormViewModel.idForm = formView!!.id
        getImagesByFormViewModel.loadImages()
        ib_bodies.visibility = View.VISIBLE
        ib_save!!.visibility = View.VISIBLE
        cb_finished!!.isEnabled = true
        initDataSpinner()
        sp_select!!.isEnabled = false

    }

    private fun insertForm(){
        if (flagNewForm){
            insertNewForm()
        }else{
            updateForm()
        }

    }

    private fun insertNewForm(){
        if (proyectView != null){
            val dateMinusFiftyYear = Calendar.getInstance().run {
                add(Calendar.YEAR, -50)
                time
            }
            val dateLong = System.currentTimeMillis()
            //val dateToCloud = String.format(Locale.US,"/Date(%d)/", dateLong)
            val dateToCloud = dateLong.toString()
            val dateInit = dateMinusFiftyYear.time
            //val dateInitToCloud = String.format(Locale.US,"/Date(%d)/", dateInit)
            val dateInitToCloud = dateInit.toString()

            val form = Form(0, proyectView!!.id, this.codeTypeForm , 1,
                    et_title.text.toString(), dateInitToCloud, 1, "None",
                    123, Variables.locationUser.lat,
                    Variables.locationUser.lon, dateToCloud, dateInitToCloud)
            insertOneFormViewModel.form = form
            insertOneFormViewModel.insertForm()

        }
        else{
            context!!.toast(getString(R.string.failure_project_not_selected))
        }

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
