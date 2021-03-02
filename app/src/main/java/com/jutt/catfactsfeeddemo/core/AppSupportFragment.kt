package com.jutt.catfactsfeeddemo.core

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

abstract class AppSupportFragment : Fragment(){

    @StringRes
    open val titleResId: Int? = null
    open val name: String by lazy { this.javaClass.simpleName }

    open fun getTitle(): String? = titleResId?.let { getString(it) }

    var backStackTransactionName: String? = null

    /**
     * Called by Activity whenever back button is invoked. The Fragment should perform the
     * desired operation (if required) and return true if Activity can continue its execution;
     * false if Fragment desires to block user here
     *
     * @return false if there is input required from user or desires to block; true otherwise
     */
    open fun onNavigateBack(): Boolean = true

}
