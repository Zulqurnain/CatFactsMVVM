package com.jutt.catfactsfeeddemo.extensions

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.URLSpan
import android.text.util.Linkify
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap
import kotlin.math.min

val Boolean.toInt get() = if (this) 1 else 0
val Long.toSeconds get() = this / 1000
val Long.toMillis get() = this * 1000
val Long.toTimeString get() = this.toString().padStart(length = 2, padChar = '0')


/**
 *
 * @receiver String
 * @param index Int
 * @return String
 */
@Throws(IndexOutOfBoundsException::class)
fun String.removeAt(index: Int): String {
    if (index < 0 || index >= length) throw IndexOutOfBoundsException()

    val endIndex = min(length, index + 1)
    return this.removeRange(index, endIndex)
}

/**
 *
 * @receiver Double
 * @param maximumPlaces Int
 * @param minimumPlaces Int
 * @return String
 */
@Throws(IllegalArgumentException::class)
fun Double.formatUpToNPlaces(maximumPlaces: Int = 2, minimumPlaces: Int = 0): String {
    if (maximumPlaces < 0) throw IllegalArgumentException("Maximum places cannot be negative")

    val formatted = "%1$.${maximumPlaces}f".format(this)
    val adjustedPlacesForDot = if (minimumPlaces == 0) minimumPlaces else minimumPlaces + 1
    val lastNPlacesToTrim =
        formatted.length - (this.toInt().toString().length + adjustedPlacesForDot)

    val decimalSeparator = DecimalFormatSymbols.getInstance().decimalSeparator

    return formatted.trimLastN(n = lastNPlacesToTrim, breakOnNegative = true, predicate = {
        it == '0' || it == decimalSeparator
    })
}

/**
 *
 * @receiver String
 * @param n Int
 * @param predicate Function1<[@kotlin.ParameterName] Char, Boolean>
 * @return String
 */
fun String.trimLastN(
    n: Int,
    predicate: (char: Char) -> Boolean,
    breakOnNegative: Boolean = false
): String {
    var result = this
    var iterator = length - 1
    var loopCount = 0
    val loopSize = min(length, n)

    while (loopCount < loopSize) {
        if (predicate(result[iterator])) {
            result = result.removeAt(iterator)
        } else if (breakOnNegative) {
            break
        }

        iterator--
        loopCount++
    }

    return result
}