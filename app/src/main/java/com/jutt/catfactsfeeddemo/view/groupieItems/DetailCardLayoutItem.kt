package com.jutt.catfactsfeeddemo.view.groupieItems

import android.view.View
import com.blankj.utilcode.util.TimeUtils
import com.jutt.catfactsfeeddemo.R
import com.jutt.catfactsfeeddemo.databinding.LayoutDetailCardBinding
import com.jutt.catfactsfeeddemo.utils.loadImageFromDrawable
import com.jutt.catfactsfeeddemo.utils.loadImageFromUrl
import com.xwray.groupie.viewbinding.BindableItem

class DetailCardLayoutItem(
    private val userTitle: String = "",
    private val imageProfileURL: String = "",
    private val dateAndTimeToShow: String = "",
    private val detailsText: String = "",
    private val textBelowDetails: String = "",
    private val textAboveDetails: String = "",
): BindableItem<LayoutDetailCardBinding>() {
    override fun initializeViewBinding(view: View): LayoutDetailCardBinding {
        return LayoutDetailCardBinding.bind(view)
    }

    override fun bind(viewBinding: LayoutDetailCardBinding, position: Int) {
        viewBinding.apply {
            titleUser.text = userTitle.ifEmpty { titleUser.context.getText(R.string.user_caps) }
            if(imageProfileURL.isEmpty())
                ivProfilePic.loadImageFromDrawable(R.drawable.ic_user)
            else
                ivProfilePic.loadImageFromUrl(imageProfileURL)
            createdAtDate.text = dateAndTimeToShow.ifEmpty { TimeUtils.getNowString() }
            factDetail.text = detailsText
            factDetailId.text = textBelowDetails
            factAttr.text = textAboveDetails
        }
    }

    override fun getLayout(): Int  = R.layout.layout_detail_card
}