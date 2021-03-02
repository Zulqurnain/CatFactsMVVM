package com.jutt.catfactsfeeddemo.application

import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.Utils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}
