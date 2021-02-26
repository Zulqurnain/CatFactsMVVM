package com.jutt.catfactsfeeddemo.architecture

import androidx.lifecycle.Observer
import com.jutt.catfactsfeeddemo.architecture.Event

/**
 * Observer class to observe event instances, the observer receives lambda function as argument
 * which is only called when the event has not been handled before once at least
 */
class EventObserver<T>(private val onEventReceived: (content: T) -> Unit) : Observer<Event<T?>> {

    override fun onChanged(event: Event<T?>?) {
        event?.getContentIfNotHandled()?.let { content -> onEventReceived(content) }
    }

}
