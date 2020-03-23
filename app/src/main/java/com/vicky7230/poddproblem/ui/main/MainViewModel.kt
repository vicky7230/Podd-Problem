package com.vicky7230.poddproblem.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonElement
import com.vicky7230.poddproblem.data.DataManager
import com.vicky7230.poddproblem.data.network.Resource
import com.vicky7230.poddproblem.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val dataManager: DataManager
) : BaseViewModel() {

    var data = MutableLiveData<Resource<MutableList<Item>>>()
    var exclusions = MutableLiveData<Resource<MutableList<Pair<Item.Exclusion, Item.Exclusion>>>>()

    fun getData() {

        viewModelScope.launch {

            data.value = Resource.Loading

            val response = safeApiCall { dataManager.getVariants() }

            when (response) {
                is Resource.Success -> {
                    val jsonObject = response.data.asJsonObject
                    val itemList = mutableListOf<Item>()
                    jsonObject["variants"].asJsonObject["variant_groups"].asJsonArray.forEach { variantGroup: JsonElement ->
                        itemList.add(
                            Item.Group(
                                variantGroup.asJsonObject["group_id"].asString,
                                variantGroup.asJsonObject["name"].asString
                            )
                        )

                        variantGroup.asJsonObject["variations"].asJsonArray.forEach { variation: JsonElement ->
                            itemList.add(
                                Item.Variant(
                                    variantGroup.asJsonObject["group_id"].asString,
                                    variation.asJsonObject["name"].asString,
                                    variation.asJsonObject["price"].asInt,
                                    variation.asJsonObject["default"].asInt,
                                    variation.asJsonObject["id"].asString,
                                    variation.asJsonObject["inStock"].asInt
                                )
                            )
                        }
                    }

                    val exclusionList: MutableList<Pair<Item.Exclusion, Item.Exclusion>> =
                        mutableListOf()
                    jsonObject["variants"].asJsonObject["exclude_list"].asJsonArray.forEach { exclusion: JsonElement ->
                        val exclusionPair = Pair(
                            Item.Exclusion(
                                exclusion.asJsonArray[0].asJsonObject["group_id"].asString,
                                exclusion.asJsonArray[0].asJsonObject["variation_id"].asString

                            ), Item.Exclusion(
                                exclusion.asJsonArray[1].asJsonObject["group_id"].asString,
                                exclusion.asJsonArray[1].asJsonObject["variation_id"].asString
                            )
                        )
                        exclusionList.add(exclusionPair)
                    }

                    data.value = Resource.Success(itemList)
                    exclusions.value = Resource.Success(exclusionList)

                }
                is Resource.Error -> {
                    data.value = response
                }
            }
        }
    }

}