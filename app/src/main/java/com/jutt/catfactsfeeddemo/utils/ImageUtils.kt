package com.jutt.catfactsfeeddemo.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.loadImageFromUrl(
    url: String,
    @DrawableRes placeHolder: Int? = null,
    radius: Int = 0
) {
    if (shouldStartLoadImage()) {
        Glide.with(this)
            .load(url)
            .apply(getRequestOptions(radius, placeHolder))
            .into(this)
    }
}

fun ImageView.loadImageFromUrl(url: String, placeHolder: Drawable, radius: Int = 0) {
    if (shouldStartLoadImage()) {
        Glide.with(this)
            .load(url)
            .apply(getRequestOptions(radius, placeHolder))
            .into(this)
    }
}

fun ImageView.loadImageFromDrawable(
    @DrawableRes drawable: Int,
    @DrawableRes placeHolder: Int? = null,
    radius: Int = 0
) {
    if (shouldStartLoadImage()) {
        Glide.with(this)
            .load(drawable)
            .apply(getRequestOptions(radius, placeHolder))
            .into(this)
    }
}

fun ImageView.loadImageFromDrawable(
    @DrawableRes drawable: Int,
    placeHolder: Drawable,
    radius: Int = 0
) {
    if (shouldStartLoadImage()) {
        Glide.with(this)
            .load(drawable)
            .apply(getRequestOptions(radius, placeHolder))
            .into(this)
    }
}

fun ImageView.loadImageFromDrawable(
    drawable: Drawable,
    @DrawableRes placeHolder: Int? = null,
    radius: Int = 0
) {
    if (shouldStartLoadImage()) {
        Glide.with(this)
            .load(drawable)
            .apply(getRequestOptions(radius, placeHolder))
            .into(this)
    }
}

fun ImageView.loadImageFromDrawable(drawable: Drawable, placeHolder: Drawable, radius: Int = 0) {
    if (shouldStartLoadImage()) {
        Glide.with(this)
            .load(drawable)
            .apply(getRequestOptions(radius, placeHolder))
            .into(this)
    }
}

fun ImageView.loadImageFromFile(
    file: File,
    @DrawableRes placeHolder: Int? = null,
    radius: Int = 0
) {
    if (shouldStartLoadImage()) {
        Glide.with(this)
            .load(Uri.fromFile(file))
            .apply(getRequestOptions(radius, placeHolder))
            .into(this)
    }
}

fun ImageView.loadImageFromBitmap(
    bitmap: Bitmap,
    @DrawableRes placeHolder: Int? = null,
    radius: Int = 0
) {
    if (shouldStartLoadImage()) {
        Glide.with(this)
            .load(bitmap)
            .apply(getRequestOptions(radius, placeHolder))
            .into(this)
    }
}

fun ImageView.loadImageFromBitmap(bitmap: Bitmap, placeHolder: Drawable, radius: Int = 0) {
    if (shouldStartLoadImage()) {
        Glide.with(this)
            .load(bitmap)
            .apply(getRequestOptions(radius, placeHolder))
            .into(this)
    }
}

private fun getRequestOptions(radius: Int, @DrawableRes placeHolder: Int?) =
    RequestOptions().apply {
        if (radius > 0) {
            transform(CenterCrop(), RoundedCorners(radius))
        }

        placeHolder?.let {
            placeholder(it)
        }
    }

private fun getRequestOptions(radius: Int, drawable: Drawable?) = RequestOptions().apply {
    if (radius > 0) {
        transform(CenterCrop(), RoundedCorners(radius))
    }

    placeholder(drawable)
}

private fun ImageView.shouldStartLoadImage(): Boolean {
    val activity = this.context as? Activity
    return !(activity?.isDestroyed == true || activity?.isFinishing == true)
}
