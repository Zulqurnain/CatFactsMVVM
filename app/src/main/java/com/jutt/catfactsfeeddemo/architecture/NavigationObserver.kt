package com.jutt.catfactsfeeddemo.architecture

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.jutt.catfactsfeeddemo.R
import com.jutt.catfactsfeeddemo.core.AppSupportFragment

class NavigationAnimation(
    @AnimatorRes @AnimRes val enter: Int,
    @AnimatorRes @AnimRes val exit: Int
) {
    companion object {
        val FADE_IN: NavigationAnimation = NavigationAnimation(
            enter = R.anim.fade_in,
            exit = R.anim.fade_out
        )
        val SLIDE_LEFT: NavigationAnimation = NavigationAnimation(
            enter = R.anim.slide_in_left,
            exit = R.anim.slide_out_left
        )
        val SLIDE_RIGHT: NavigationAnimation = NavigationAnimation(
            enter = R.anim.slide_in_right,
            exit = R.anim.slide_out_right
        )
    }
}

class NavigationInstance(
    @IdRes val frameId: Int = R.id.content,
    val fragment: AppSupportFragment,
    val addToBackStack: Boolean = true,
    val pushAnimation: NavigationAnimation? = NavigationAnimation.SLIDE_LEFT,
    val popAnimation: NavigationAnimation? = NavigationAnimation.SLIDE_RIGHT
)

interface NavigationListener {
    /**
     * Callback to confirm if the navigation event is supposed for Fragment navigation transaction or not
     * @return Returns true for Fragment navigation; false otherwise
     */
    fun isFragmentNavigation(event: String): Boolean

    /**
     * Handles the navigation event that is not Fragment navigation
     */
    fun handleNonFragmentNavigationInstance(event: String)

    /**
     * Method likely to find transaction name from visible fragment
     * @return Returns name for last event transaction for given event
     */
    fun getLastTransactionEvent(event: String): String?

    /**
     * Method likely to perform pop operation immediately inside of the call and confirm if the
     * navigation transaction should be made or not
     * @return Returns true if transaction is not required, else false
     */
    fun popFragmentFromStack(event: String): Boolean

    /**
     * Method responsible for creating Fragment navigation instance with transaction properties
     * required for this transaction
     * @return Returns NavigationInstance for the desired transaction
     */
    fun getFragmentNavigationInstance(event: String): NavigationInstance

    /**
     * Responsible for running FragmentTransaction actions for the given navigation event instance
     */
    fun handleNavigationTransaction(
        event: String,
        instance: NavigationInstance,
        action: FragmentTransaction.() -> Unit
    )
}

class NavigationObserver(private val listener: NavigationListener) : Observer<Event<String>> {

    override fun onChanged(event: Event<String>?) {
        val content: String = event?.getContentIfNotHandled() ?: return
        KeyboardUtils.hideSoftInput(ActivityUtils.getTopActivity())
        if (!listener.isFragmentNavigation(event = content)) {
            listener.handleNonFragmentNavigationInstance(event = content)
            return
        }
        if (!listener.popFragmentFromStack(content)) {
            // Get navigation Fragment instance
            val instance = listener.getFragmentNavigationInstance(content)
            // Get reference to last transaction pop the right transaction from back stack when required
            val backStackTransactionName = listener.getLastTransactionEvent(content)
            // Event name so this transaction can be added to back stack in next transaction if required
            instance.fragment.backStackTransactionName = content
            // Handles navigation with given attributes
            listener.handleNavigationTransaction(event = content, instance = instance) {
                // Setting transaction animations
                if (instance.pushAnimation != null) {
                    if (instance.popAnimation != null) {
                        setCustomAnimations(
                            instance.pushAnimation.enter,
                            instance.pushAnimation.exit,
                            instance.popAnimation.enter,
                            instance.popAnimation.exit
                        )
                    } else {
                        setCustomAnimations(
                            instance.pushAnimation.enter,
                            instance.pushAnimation.exit
                        )
                    }
                }
                replace(instance.frameId, instance.fragment)
                if (instance.addToBackStack) {
                    addToBackStack(backStackTransactionName)
                }
            }
        }
    }

}
