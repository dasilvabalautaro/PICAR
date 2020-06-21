package com.empoderar.picar.presentation.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import cc.duduhuo.util.crypto.AES
import com.empoderar.picar.R
import com.empoderar.picar.domain.data.Permission
import com.empoderar.picar.model.persistent.caching.Constants
import com.empoderar.picar.model.persistent.caching.Variables
import com.empoderar.picar.model.persistent.preference.PreferenceRepository
import com.empoderar.picar.model.persistent.preference.PreferenceRepository.set
import com.empoderar.picar.presentation.extension.failure
import com.empoderar.picar.presentation.extension.observe
import com.empoderar.picar.presentation.extension.viewModel
import com.empoderar.picar.presentation.navigation.Navigator
import com.empoderar.picar.presentation.plataform.BaseFragment
import com.empoderar.picar.presentation.plataform.NetworkHandler
import com.empoderar.picar.presentation.presenter.PermissionViewModel
import com.empoderar.picar.presentation.tools.Validate
import com.empoderar.picar.presentation.view.activities.LoginActivity
import kotlinx.android.synthetic.main.view_login.*
import javax.inject.Inject

class LoginFragment: BaseFragment() {
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var networkHandler: NetworkHandler

    private lateinit var permissionViewModel: PermissionViewModel
    private lateinit var prefs: SharedPreferences
    private lateinit var email: String
    private lateinit var loginUser: String
    private lateinit var passwordUser: String

    override fun layoutId() = R.layout.view_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        permissionViewModel = viewModel(viewModelFactory) {
            observe(result, ::handlePermission)
            failure(failure, ::handleFailure)
        }

        this.prefs = PreferenceRepository.customPrefs(requireActivity(),
                Constants.preference_picar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ib_access.setOnClickListener { checkPermission() }
        bt_change_user.setOnClickListener {
            enableControls(true)
            this.email = ""
            this.loginUser = ""
            this.passwordUser =  ""
        }
//        verifyEmail()
//
//        et_user.setText("dasilvaba")
//        et_password.setText("It@p@llu1962")
        controlAccess()
    }

    private fun verifyUserData(): Boolean{
        this.email = this.prefs.getString(Constants.prefEmail, "").toString()
         val password = this.prefs.getString(Constants.prefPassword, "").toString()
        this.loginUser = this.prefs.getString(Constants.prefLogin, "").toString()
        if (this.email.trim().isNotEmpty() && password.trim().isNotEmpty()
                && this.loginUser.trim().isNotEmpty()){
            this.passwordUser = password.let { AES.decrypt(it, Constants.seed) }
            return true
        }

        return false
    }

    private fun controlAccess(){
        if (verifyUserData()){
            if ((networkHandler.isConnected || Variables.isServerUp)){
                checkPermissionCloud()
            }else {
                notify(R.string.failure_network_connection)
                defineAccess()
            }

        }else{
            enableControls(true)
        }
    }

//    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
//    private fun verifyEmail(){
//        this.email = this.prefs.getString(Constants.prefEmail, "").toString()
//        if (email.trim().isEmpty()){
//            enableControls(true)
//        }else{
//            enableControls(false)
//        }
//
//    }
    private fun enableControls(value: Boolean){
        if (value){
            et_email.visibility = View.VISIBLE
            iv_email.visibility = View.VISIBLE
        }else{
            et_email.visibility = View.INVISIBLE
            iv_email.visibility = View.INVISIBLE
        }
    }


    private fun handlePermission(permission: Permission?){
        if (permission != null){
            this.prefs[Constants.prefToken] = permission.token
            this.prefs[Constants.prefExpiration] = permission.expiration
            this.prefs[Constants.prefEmail] = permission.email
            this.prefs[Constants.prefLogin] = et_user.text.toString()
            this.prefs[Constants.prefIdUser] = permission.id
            this.prefs[Constants.prefUnity] = permission.unity
            val password = AES.encrypt(et_password.text.toString(), Constants.seed)
            this.prefs[Constants.prefPassword] = password
            //println(permission.expiration) 2020-03-05T17:59:44.1443949Z
            defineAccess()

        }
    }

    private fun defineAccess(){
        val isDown = this.prefs.getInt(Constants.prefIsUnitiesDownload, 0)
        if (isDown == 0){
            navigator.showDownload(requireActivity())
        }else{
            navigator.showMenu(requireActivity())
        }
        (requireActivity() as LoginActivity).finish()
    }

    private fun checkPermission(){
        if (validatedInput(et_user.text.toString(),
                        et_password.text.toString(), et_email.text.toString()) &&
                (networkHandler.isConnected || Variables.isServerUp)){
            checkPermissionCloud()

//            if ((!networkHandler.isConnected || !Variables.isServerUp) && isRegisterPermission()){
//                checkPermissionLocal()
//            }else{
//                checkPermissionCloud()
//            }
        }else{
            notify(R.string.failure_input)
        }
    }

//    private fun isRegisterPermission(): Boolean{
//        val password = this.prefs.getString(Constants.prefPassword, "")
//        return !password.isNullOrEmpty()
//    }


    private fun checkPermissionCloud(){
//        if (this.email.isEmpty() && Validate.isValidEmail(et_email.text.toString())){
//            this.email = et_email.text.toString()
//        }

        //if (this.email.isNotEmpty()){
        permissionViewModel.url = String.format("${Constants.urlBase}${Constants.serviceLogin}")
        permissionViewModel.email = this.email
        permissionViewModel.login = this.loginUser
        permissionViewModel.password = this.passwordUser
        if (permissionViewModel.verifyInput()){
            permissionViewModel.requestPermission()
        }
//        }else{
//            notify(R.string.msg_email_not_found)
//        }
    }

//    private fun checkPermissionLocal(){
//        if (validatedInput(et_user.text.toString(),
//                        et_password.text.toString(), this.email)){
//
//            val password = this.prefs.getString(Constants.prefPassword, "")
//            val login = this.prefs.getString(Constants.prefLogin, "")
//            val decrypt = password?.let { AES.decrypt(it, Constants.seed) }
//            if (et_user.text.toString() == login && et_password.text.toString() == decrypt){
//                navigator.showMenu(requireActivity())
//                (requireActivity() as LoginActivity).finish()
//            }else{
//                notify(R.string.msg_user_not_found)
//            }
//
//        }else{
//            notify(R.string.failure_input)
//        }
//
//    }

    override fun renderFailure(message: Int) {
        notify(message)
    }

    private fun validatedInput(name: String, password: String, email: String): Boolean{
        if(name.length > 4 && password.length > 5 && Validate.isValidEmail(email)){
            this.email = email;
            this.passwordUser = password
            this.loginUser = name
            return true
        }
        return false
    }
}