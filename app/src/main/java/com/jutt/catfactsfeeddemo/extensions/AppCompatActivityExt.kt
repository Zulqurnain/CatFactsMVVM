package com.jutt.catfactsfeeddemo.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.Menu
import androidx.annotation.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


fun AppCompatActivity.setupActionBar(toolbar: Toolbar?, action: ActionBar.() -> Unit) {
    setSupportActionBar(toolbar)
    supportActionBar?.run {
        action()
    }
}
@SuppressLint("ObsoleteSdkInt")
fun AppCompatActivity.refreshMenu(menu: Menu) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        invalidateOptionsMenu()
    } else {
        menu.clear()
        onCreateOptionsMenu(menu)
    }
}

fun AppCompatActivity.hasOpenedDialogs(): Boolean {
    val fragments: List<Fragment?> = supportFragmentManager.fragments
    for (fragment in fragments) {
        if (fragment is DialogFragment && fragment.isShowing()) {
            return true
        }
    }
    return false
}

fun AppCompatActivity.replaceFragmentInActivity(
    fragment: Fragment,
    frameId: Int,
    addToBackStack: Boolean = false,
    backStackEntry: String? = null,
    @AnimatorRes @AnimRes @Size(value = 2) pushAnimation: IntArray? = null,
    @AnimatorRes @AnimRes @Size(value = 2) popAnimation: IntArray? = null
) = supportFragmentManager.transact(
    addToBackStack = addToBackStack,
    backStackEntry = backStackEntry,
    pushAnimation = pushAnimation,
    popAnimation = popAnimation
) {
    replace(frameId, fragment)
}

fun Activity.launchDialIntent(number: String?) {
    if (!number.isNullOrBlank()) {
        startActivity(Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$number")
        })
    }
}

fun AppCompatActivity.getFragmentFromContainer(@IdRes containerId: Int): Fragment? {
    return supportFragmentManager.findFragmentById(containerId)
}

