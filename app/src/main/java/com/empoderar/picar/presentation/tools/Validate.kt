package com.empoderar.picar.presentation.tools

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import com.empoderar.picar.model.persistent.caching.Constants
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


object Validate {
    fun isValidEmail(value: CharSequence?): Boolean{
        return if (value == null){
            false
        }else{
            android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()
        }
    }

    fun isURLReachable(context: Context): Boolean {
        val connectivityManager = context
                .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        if (netInfo != null && netInfo.isConnected) {
            try {
                val url = URL(Constants.server)
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 3 * 1000          // 2 s.
                urlConnection.connect()
                return if (urlConnection.responseCode == 200) {        // 200 = "OK" code (http connection is fine).
                    println("Connection Success!!")
                    true
                } else {
                    false
                }
            } catch (e1: MalformedURLException) {
                return false
            } catch (e: IOException) {
                return false
            }

        }
        return false
    }
}