package com.jutt.catfactsfeeddemo.core

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.jutt.catfactsfeeddemo.R
import com.jutt.catfactsfeeddemo.architecture.NavigationInstance
import com.jutt.catfactsfeeddemo.architecture.NavigationListener
import dagger.hilt.android.AndroidEntryPoint

abstract class AppNavigationActivity : AppSupportActivity(), NavigationListener {

    val findVisibleFragment: AppSupportFragment?
        get() = supportFragmentManager.findFragmentById(R.id.content) as? AppSupportFragment

    override fun onBackPressed() {
        if (findVisibleFragment?.onNavigateBack() != false) {
            handleBackPressed()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        supportFragmentManager.addOnBackStackChangedListener {
            setDisplayTitle()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    open fun handleBackPressed() {
        super.onBackPressed()
    }

    fun clearBackStack() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun isFragmentNavigation(event: String): Boolean {
        return true
    }

    override fun handleNonFragmentNavigationInstance(event: String) {
        throw RuntimeException("$event navigation not handled")
    }

    override fun getLastTransactionEvent(event: String): String? {
        return findVisibleFragment?.backStackTransactionName
    }

    override fun popFragmentFromStack(event: String): Boolean {
        return supportFragmentManager.popBackStackImmediate(
            event,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun getFragmentNavigationInstance(event: String): NavigationInstance {
        throw RuntimeException("$event navigation not handled")
    }

    override fun handleNavigationTransaction(
        event: String,
        instance: NavigationInstance,
        action: FragmentTransaction.() -> Unit
    ) {
        supportFragmentManager.beginTransaction().apply {
            action()
            if (!instance.addToBackStack) {
                runOnCommit {
                    setDisplayTitle(fragment = instance.fragment)
                }
            }
        }.commit()
        if (instance.addToBackStack) {
            setDisplayTitle(fragment = instance.fragment)
        }
    }

    open fun setDisplayTitle(fragment: AppSupportFragment? = findVisibleFragment) {
        if (fragment?.isAdded == true) {
            title = (fragment.getTitle() ?: return)
        } else {
            setTitle(fragment?.titleResId ?: return)
        }
    }

}
