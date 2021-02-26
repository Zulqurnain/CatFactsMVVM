package com.jutt.catfactsfeeddemo.architecture

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
class Event<out T> private constructor(
    private val content: T,
    val extras: HashMap<String, Any?>? = null
) {

    companion object {
        fun empty(): Event<Any> = Event(content = Any())
        fun <T> create(content: T): Event<T> = Event(content = content)
        fun <T> create(content: T, extras: HashMap<String, Any?>?): Event<T> =
            Event(content = content, extras = extras)
    }

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
