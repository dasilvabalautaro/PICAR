package com.empoderar.picar.presentation.presenter

import androidx.lifecycle.MutableLiveData
import android.webkit.URLUtil
import com.empoderar.picar.domain.data.Permission
import com.empoderar.picar.domain.interactor.GetPermissionUseCase
import com.empoderar.picar.presentation.plataform.BaseViewModel
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class PermissionViewModel @Inject constructor(private val getPermissionUseCase:
                                              GetPermissionUseCase): BaseViewModel() {
    var result: MutableLiveData<Permission> = MutableLiveData()

    lateinit var url: String
    lateinit var login: String
    lateinit var email: String
    lateinit var password: String
    private lateinit var params: List<String>

    fun verifyInput(): Boolean{
        if (URLUtil.isValidUrl(this.url) &&
                this.login.isNotEmpty() &&
                this.email.isNotEmpty() &&
                this.password.isNotEmpty()){
            val jsonString = jsonToString()
            if (jsonString.isNotEmpty()){
                this.params = listOf(this.url, jsonString)
                return true
            }

        }
        return false
    }

    private fun jsonToString(): String{
        val jsonObject = JSONObject()
        try {
            jsonObject.put("Login", this.login)
            jsonObject.put("Email", this.email)
            jsonObject.put("Password", this.password)
            return jsonObject.toString(2)
        }catch (exception: JSONException){
            println(exception.message)
        }
        return ""
    }

    fun requestPermission() = getPermissionUseCase(this.params){
        it.either(::handleFailure, ::handleResult)
    }

    private fun handleResult(permission: Permission){

        result.value = permission

    }

}