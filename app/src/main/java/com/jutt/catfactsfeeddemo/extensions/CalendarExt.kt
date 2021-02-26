package com.jutt.catfactsfeeddemo.extensions

import android.provider.Settings.System.DATE_FORMAT
import com.blankj.utilcode.util.TimeUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

var Calendar.timeInSeconds
    get() = timeInMillis.toSeconds
    set(value) {
        timeInMillis = value.toMillis
    }

fun Date.plusMillis(millis: Long) = Date(this.time + millis)

fun Date.plusSeconds(seconds: Long) = Date(this.time + TimeUnit.SECONDS.toMillis(seconds))

fun Date.to12HourTimeString() = TimeUtils.date2String(
    this,
    "hh:mm a"
)