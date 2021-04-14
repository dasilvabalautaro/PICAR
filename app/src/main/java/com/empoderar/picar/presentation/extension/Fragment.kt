@file:Suppress("DEPRECATION")

package com.empoderar.picar.presentation.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.empoderar.picar.presentation.plataform.BaseActivity
import com.empoderar.picar.presentation.plataform.BaseFragment
import kotlinx.android.synthetic.main.activity_task.*


inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().func().commit()

inline fun <reified T : ViewModel> Fragment.viewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}


fun BaseFragment.close() = (activity as FragmentActivity).supportFragmentManager.popBackStack()

val BaseFragment.viewContainer: View
    get() = (activity as BaseActivity)
            .fragmentContainer

val BaseFragment.appContext: Context get() = activity?.applicationContext!!