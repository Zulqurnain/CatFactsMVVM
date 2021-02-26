package com.jutt.catfactsfeeddemo.extensions

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.Size
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * Runs a FragmentTransaction, then calls commit().
 */
inline fun FragmentManager.transact(
    addToBackStack: Boolean = false,
    backStackEntry: String? = null,
    @AnimatorRes @AnimRes @Size(2) pushAnimation: IntArray? = null,
    @AnimatorRes @AnimRes @Size(2) popAnimation: IntArray? = null,
    action: FragmentTransaction.() -> Unit
) = beginTransaction().apply {
    if (popAnimation != null && pushAnimation != null) {
        setCustomAnimations(
            pushAnimation.getOrNull(0) ?: 0,
            pushAnimation.getOrNull(1) ?: 0,
            popAnimation.getOrNull(0) ?: 0,
            popAnimation.getOrNull(1) ?: 0
        )
    } else if (popAnimation != null && pushAnimation != null) {
        setCustomAnimations(pushAnimation.getOrNull(0) ?: 0, pushAnimation.getOrNull(1) ?: 0)
    }
    action()
    if (addToBackStack || (backStackEntry?.isNotBlank() == true)) {
        addToBackStack(backStackEntry)
    }
}.commitAllowingStateLoss()

