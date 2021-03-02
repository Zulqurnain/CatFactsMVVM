package com.jutt.catfactsfeeddemo.view.groupieItems

import android.graphics.drawable.Animatable
import android.view.View
import com.blankj.utilcode.util.TimeUtils
import com.jutt.catfactsfeeddemo.R
import com.jutt.catfactsfeeddemo.data.models.AnimalFact
import com.jutt.catfactsfeeddemo.databinding.LayoutDetailCardBinding
import com.jutt.catfactsfeeddemo.databinding.LayoutTextItemBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.Item
import java.util.*

class ExpandableCatFactItem(
    private val itemData: AnimalFact
) : TextLayoutItem(
    value = itemData.text
), ExpandableItem {

    private var expandableGroup: ExpandableGroup? = null

    override fun bind(viewBinding: LayoutTextItemBinding, position: Int) {
        super.bind(viewBinding, position)
        viewBinding.icon.visibility = View.VISIBLE
        viewBinding.icon.setImageResource(if (expandableGroup!!.isExpanded) R.drawable.collapse else R.drawable.expand)
        viewBinding.icon.setOnClickListener {
            expandableGroup!!.onToggleExpanded()
            bindIcon(viewBinding)
        }
    }

    private fun bindIcon(viewBinding: LayoutTextItemBinding) {
        viewBinding.icon.visibility = View.VISIBLE
        viewBinding.icon.setImageResource(if (expandableGroup!!.isExpanded) R.drawable.collapse_animated else R.drawable.expand_animated)
        val drawable = viewBinding.icon.drawable as Animatable
        drawable.start()
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    override fun isSameAs(other: Item<*>): Boolean {
        if (other !is TextLayoutItem) return false
        return itemData.text == other.value
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        if (other !is TextLayoutItem) return false
        return itemData.text == other.value
    }
}