package com.jutt.catfactsfeeddemo.view.groupieItems

import android.view.View
import androidx.annotation.StringRes
import com.jutt.catfactsfeeddemo.R
import com.jutt.catfactsfeeddemo.databinding.LayoutTextItemBinding
import com.xwray.groupie.viewbinding.BindableItem

open class TextLayoutItem(
    val value: String ? = null,
    private val onClick: View.OnClickListener? = null
): BindableItem<LayoutTextItemBinding>() {
    override fun initializeViewBinding(view: View): LayoutTextItemBinding {
        return LayoutTextItemBinding.bind(view)
    }

    override fun bind(viewBinding: LayoutTextItemBinding, position: Int) {
        viewBinding.apply {
            textView.text = value
            icon.setOnClickListener(onClick)
        }
    }

    override fun getLayout(): Int  = R.layout.layout_text_item
}