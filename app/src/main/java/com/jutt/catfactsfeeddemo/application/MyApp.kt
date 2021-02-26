package com.jutt.catfactsfeeddemo.application

import android.webkit.WebView
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.SDCardUtils
import com.blankj.utilcode.util.Utils
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : MultiDexApplication() {

//    var injector: Injector? = null
//        private set
//
//    fun onResetUserSettings() {
//        injector = Injector(this)
//    }

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                WebView.setWebContentsDebuggingEnabled(true)
//            }
//            CrashUtils.init(SDCardUtils.getSDCardPathByEnvironment()+"MyGalleriaCrashes"){}
//        } else {
////            Timber.plant(CrashReportingTree())
//        }
//        onResetUserSettings()
    }

}
