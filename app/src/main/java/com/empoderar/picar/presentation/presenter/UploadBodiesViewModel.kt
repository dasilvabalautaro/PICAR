package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import android.webkit.URLUtil
import com.empoderar.picar.domain.data.Message
import com.empoderar.picar.domain.interactor.UploadFormUseCase
import com.empoderar.picar.model.persistent.network.entity.BodyEntity
import com.empoderar.picar.presentation.data.BodyFormView
import com.empoderar.picar.presentation.plataform.BaseViewModel
import com.google.gson.Gson
import java.util.*
import javax.inject.Inject

class UploadBodiesViewModel @Inject constructor(private val uploadFormUseCase:
                                                UploadFormUseCase):
        BaseViewModel() {
    var result: MutableLiveData<Message> = MutableLiveData()

    lateinit var url: String
    lateinit var token: String
    lateinit var bodies: List<BodyFormView>
    private lateinit var params: List<String>

    fun buildParams(): Boolean{
        if (URLUtil.isValidUrl(this.url) &&
                this.token.isNotEmpty()){
            val list = bodies.map {
                it.code?.let { it1 ->
                    it.value?.let { it2 ->
                        it.description?.let { it3 ->
                            it.satisfy?.let { it4 ->
                                it.comment?.let { it5 ->
                                    BodyEntity(it.id,
                                            it.formId, it.idProject, it1, it2,
                                            it3, it4,
                                            String.format(Locale.US,"/Date(%d)/", it.date?.toLong()),
                                            it5)
                                }
                            }
                        }
                    }
                }
            }
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