package com.empoderar.picar.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import android.webkit.URLUtil
import com.empoderar.picar.domain.data.Message
import com.empoderar.picar.domain.interactor.UploadFormUseCase
import com.empoderar.picar.model.persistent.network.entity.ImageEntity
import com.empoderar.picar.presentation.data.ImageView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import com.google.gson.Gson
import java.util.*
import javax.inject.Inject

class UploadImagesViewModel @Inject constructor(private val uploadFormUseCase:
                                                UploadFormUseCase):
        BaseViewModel() {
    var result: MutableLiveData<Message> = MutableLiveData()

    lateinit var url: String
    lateinit var token: String
    lateinit var images: List<ImageView>
    private lateinit var params: List<String>

    fun buildParams(): Boolean{
        if (URLUtil.isValidUrl(this.url) &&
                this.token.isNotEmpty()){
            val list = images.map { ImageEntity(it.id,
                    it.form, it.project, it.base64, it.latitude,
                    it.longitude,
                    String.format(Locale.US,"/Date(%d)/", it.date.toLong())) }
            val gson = Gson()
            val jsonString = gson.toJson(list)
            println(jsonString)
            if (jsonString.isNotEmpty()){
                this.params = listOf(this.token, this.url, jsonString)
                return true
            }

        }
        return false

    }

    fun requestPost() = uploadFormUseCase(this.params){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(value: Message){
        result.value = value

    }

}