package com.jutt.catfactsfeeddemo.utils

import android.app.Activity
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

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

private fun getRequestOptions(radius: Int, @DrawableRes placeHolder: Int?) =
    RequestOptions().apply {
        if (radius > 0) {
            transform(CenterCrop(), RoundedCorners(radius))
        }

        placeHolder?.let {
            placeholder(it)
        }
    }

private fun ImageView.shouldStartLoadImage(): Boolean {
    val activity = this.context as? Activity
    return !(activity?.isDestroyed == true || activity?.isFinishing == true)
}
