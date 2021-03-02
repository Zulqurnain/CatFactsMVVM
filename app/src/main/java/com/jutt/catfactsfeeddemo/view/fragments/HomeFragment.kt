package com.jutt.catfactsfeeddemo.view.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.TimeUtils
import com.jutt.catfactsfeeddemo.R
import com.jutt.catfactsfeeddemo.application.Contants.FRIENDLY_DATE_TIME_FORMAT
import com.jutt.catfactsfeeddemo.application.Contants.SERVER_DATE_TIME_FORMAT
import com.jutt.catfactsfeeddemo.core.BaseFragment
import com.jutt.catfactsfeeddemo.data.models.AnimalFact
import com.jutt.catfactsfeeddemo.databinding.FragmentHomeBinding
import com.jutt.catfactsfeeddemo.view.groupieItems.DetailCardLayoutItem
import com.jutt.catfactsfeeddemo.view.groupieItems.ExpandableCatFactItem
import com.jutt.catfactsfeeddemo.viewmodels.HomeViewModel
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val titleResId: Int
        get() = R.string.app_name

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel by activityViewModels<HomeViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var expandableAdapterForFacts: GroupieAdapter

    override fun onReady() {
        setUpObservers()
        setUpViews()

        viewModel.syncCatFactsFromServer()
    }

    private fun setUpObservers() {
        viewModel.catFactsList.observe(viewLifecycleOwner, { bindItemsToAdapter(it ?: listOf()) })
    }

    private fun bindItemsToAdapter(items: List<AnimalFact>) {
        expandableAdapterForFacts.update(
            items.map {
                ExpandableGroup(
                    ExpandableCatFactItem(itemData = it),
                    false
                ).apply {
                    add(
                        DetailCardLayoutItem(
                            userTitle = it.source,
                            dateAndTimeToShow =
                            TimeUtils.date2String(
                                TimeUtils.string2Date(it.updatedAt, SERVER_DATE_TIME_FORMAT),
                                FRIENDLY_DATE_TIME_FORMAT
                            ),
                            detailsText = it.text,
                            textBelowDetails = "ID:"+it.id,
                            textAboveDetails = it.type+" | Verified:"+it.status.verified,
                        )
                    )
                }
            }
        )
    }

    private fun setUpViews() {
        expandableAdapterForFacts = GroupieAdapter()
        with(binding.recyclerView) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context)
            adapter = expandableAdapterForFacts
        }
    }
}