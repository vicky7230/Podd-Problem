package com.vicky7230.poddproblem.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vicky7230.poddproblem.R
import com.vicky7230.poddproblem.data.network.Resource
import com.vicky7230.poddproblem.ui.base.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity(), ItemListAdapter.Callback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: ItemListAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var mainViewModel: MainViewModel

    private lateinit var exclusionList: MutableList<Pair<Item.Exclusion, Item.Exclusion>>
    private var itemsNotTobeSelected: MutableList<Item.Variant> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter.setCallback(this)

        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        init()
    }

    private fun init() {

        setSupportActionBar(toolbar)

        variant_list.layoutManager = linearLayoutManager
        variant_list.adapter = adapter

        mainViewModel.data.observe(this, Observer {
            when (it) {
                is Resource.Loading -> showLoading()
                is Resource.Error -> {
                    hideLoading()
                    showError(it.exception.localizedMessage)
                }
                is Resource.Success -> {
                    hideLoading()
                    adapter.updateItems(it.data)
                    Timber.d(it.toString())
                }
            }
        })

        mainViewModel.exclusions.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    hideLoading()
                    exclusionList = it.data
                    Timber.d(it.toString())
                }
            }
        })

        mainViewModel.getData()
    }

    override fun onItemClick(item: Item.Variant, position: Int) {
        for (i in itemsNotTobeSelected.indices) {
            if (itemsNotTobeSelected[i].groupId == item.groupId &&
                itemsNotTobeSelected[i].id == item.id
            ) {
                showMessage("This combination is not allowed.")
                return
            }
        }

        selectVariant(item)

        getItemsNotToBeSelected()

    }

    private fun getItemsNotToBeSelected() {
        itemsNotTobeSelected = arrayListOf()

        adapter.getSelectedItems().forEach {

            for (i in exclusionList.indices)
                if (exclusionList[i].first.groupId == (it as Item.Variant).groupId &&
                    exclusionList[i].first.variationId == (it as Item.Variant).id
                ) {

                    itemsNotTobeSelected.add(
                        Item.Variant(
                            groupId = exclusionList[i].second.groupId,
                            id = exclusionList[i].second.variationId
                        )
                    )

                } else if (exclusionList[i].second.groupId == (it as Item.Variant).groupId &&
                    exclusionList[i].second.variationId == (it as Item.Variant).id
                ) {

                    itemsNotTobeSelected.add(
                        Item.Variant(
                            groupId = exclusionList[i].first.groupId,
                            id = exclusionList[i].first.variationId
                        )
                    )
                }
        }
    }

    private fun selectVariant(item: Item.Variant) {
        val items = adapter.getGroupItems(item.groupId)

        items.forEach {
            if ((it as Item.Variant) != item)
                it.isSelected = false
        }
        adapter.notifyDataSetChanged()

        item.isSelected = !item.isSelected

        adapter.notifyDataSetChanged()
    }

}
