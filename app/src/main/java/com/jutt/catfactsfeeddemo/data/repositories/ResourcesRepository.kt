package com.jutt.catfactsfeeddemo.data.repositories

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject
import javax.inject.Singleton

class ResourcesRepository @Inject constructor(private val context: Context) {

    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String =
            context.getString(resId, *formatArgs)

}