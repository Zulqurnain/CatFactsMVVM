package com.jutt.catfactsfeeddemo.data.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepository @Inject constructor(@ApplicationContext val context: Context) {

//    fun setLastEquipmentsCheckTime(time: Long) = saveValue(
//        context = context,
//        key = Keys.LAST_EQUIPMENTS_CHECK_TIME,
//        value = time
//    )
//
//    fun getLastEquipmentsCheckTime(): Long = getValue(
//        context = context,
//        key = Keys.LAST_EQUIPMENTS_CHECK_TIME
//    ) ?: 0

    object Files {
        const val USER_PREFS: String = "USER_PREFS"
        const val DEVICE_PREFS: String = "DEVICE_PREFS"
    }

    object Keys {
        const val PRIVACY_POLICY_URL: String = "PRIVACY_POLICY_URL"
        const val TERMS_AND_CONDITIONS_URL: String = "TERMS_AND_CONDITIONS_URL"
        const val FAQ_URL: String = "FAQ_URL"
    }

    companion object {
        private fun clear(context: Context, file: String = Files.USER_PREFS): Boolean {
            val sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE)
            return with(sharedPref.edit()) {
                clear()
                return@with commit()
            }
        }

        private fun remove(
            context: Context,
            file: String = Files.USER_PREFS,
            key: String
        ): Boolean {
            val sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE)
            return with(sharedPref.edit()) {
                remove(key)
                return@with commit()
            }
        }

        private fun <T> saveValue(
            context: Context,
            file: String = Files.USER_PREFS,
            key: String,
            value: T?
        ): Boolean {

            val sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE)
            return with(sharedPref.edit()) {
                when (value) {
                    is String -> putString(key, value)
                    is Boolean -> putBoolean(key, value)
                    is Long -> putLong(key, value)
                    is Int -> putInt(key, value)
                    is Float -> putFloat(key, value)
                }
                return@with commit()
            }
        }

        private inline fun <reified T> getValue(
            context: Context,
            file: String = Files.USER_PREFS,
            key: String,
            fallback: T? = null
        ): T? {
            val sharedPref = context.getSharedPreferences(file, Context.MODE_PRIVATE)
            return when {
                (T::class == String::class && fallback is String) -> sharedPref.getString(
                    key,
                    fallback
                ) as T
                (T::class == String::class) -> sharedPref.getString(key, null) as T
                (T::class == Boolean::class && fallback is Boolean) -> sharedPref.getBoolean(
                    key,
                    fallback
                ) as T
                (T::class == Boolean::class) -> sharedPref.getBoolean(key, false) as T
                (T::class == Long::class && fallback is Long) -> sharedPref.getLong(
                    key,
                    fallback
                ) as T
                (T::class == Long::class) -> sharedPref.getLong(key, 0) as T
                (T::class == Int::class && fallback is Int) -> sharedPref.getInt(key, fallback) as T
                (T::class == Int::class) -> sharedPref.getInt(key, 0) as T
                (T::class == Float::class) -> sharedPref.getFloat(key, 0f) as T
                (T::class == Float::class && fallback is Float) -> sharedPref.getFloat(
                    key,
                    fallback
                ) as T
                else -> null
            }
        }
    }
}
