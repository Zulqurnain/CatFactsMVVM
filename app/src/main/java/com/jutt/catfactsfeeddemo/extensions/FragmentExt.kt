package com.jutt.catfactsfeeddemo.extensions

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI
import android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.MainThread
import androidx.annotation.Size
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

fun Fragment.hasOpenedDialogs(): Boolean {
    val fragments: List<Fragment?> = activity?.supportFragmentManager?.fragments ?: listOf()
    for (fragment in fragments) {
        if (fragment is DialogFragment && fragment.isShowing()) {
            return true
        }
    }
    return false
}

fun Fragment.replaceFragmentInFragment(
    fragment: Fragment,
    frameId: Int,
    addToBackStack: Boolean = false,
    backStackEntry: String? = null,
    @AnimatorRes @AnimRes @Size(2) pushAnimation: IntArray? = null,
    @AnimatorRes @AnimRes @Size(2) popAnimation: IntArray? = null
) = childFragmentManager.transact(
    addToBackStack = addToBackStack,
    backStackEntry = backStackEntry,
    pushAnimation = pushAnimation,
    popAnimation = popAnimation
) {
    replace(frameId, fragment)
}

/**
 * An Extension function for cleaning the boiler plate code of the static newInstance method in each
 * fragment. All required fields must be added in the lambda method passed as the parameter
 *
 * <pre>
 * LoginFragment.newInstance(firstName: String, age: Int) = LoginFragment().withArgs {
 *     putString(BUNDLE_KEY_NAME, firstName)
 *     putInt(BUNDLE_KEY_AGE, age)
 * }
 * </pre>
 */
inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T {
    return this.apply {
        arguments = Bundle().apply(argsBuilder)
    }
}
