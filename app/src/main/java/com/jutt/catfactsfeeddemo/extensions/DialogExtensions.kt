package com.jutt.catfactsfeeddemo.extensions

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.jutt.catfactsfeeddemo.architecture.Event

fun DialogFragment?.isShowing() =
            this != null && dialog != null && dialog?.isShowing == true && !isRemoving


