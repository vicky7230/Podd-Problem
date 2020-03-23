package com.vicky7230.poddproblem.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vicky7230.poddproblem.R
import kotlinx.android.synthetic.main.group_list_item.view.*
import kotlinx.android.synthetic.main.variant_list_item.view.*

class ItemListAdapter(val itemList: MutableList<Item>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Callback {
        fun onItemClick(item: Item.Variant, position: Int)
    }

    private var callback: Callback? = null

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    private val TYPE_GROUP = 1
    private val TYPE_VARIANT = 2

    fun updateItems(itemList: MutableList<Item>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    fun getGroupItems(groupId: String): MutableList<Item> {
        return itemList.filter { item: Item ->
            (item is Item.Variant && item.groupId.equals(groupId, true))
        } as MutableList<Item>
    }

    fun getSelectedItems(): MutableList<Item> {
        return itemList.filter { item: Item ->
            (item is Item.Variant && item.isSelected)
        } as MutableList<Item>
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position] is Item.Variant) TYPE_VARIANT else TYPE_GROUP
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_VARIANT ->
                createVariantViewHolder(parent)
            else ->
                createGroupViewHolder(parent)
        }
    }

    private fun createGroupViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val groupViewHolder =
            GroupViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.group_list_item,
                    parent,
                    false
                )
            )

        return groupViewHolder
    }

    private fun createVariantViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val variantViewHolder =
            VariantViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.variant_list_item,
                    parent,
                    false
                )
            )

        variantViewHolder.itemView.setOnClickListener {
            if (callback != null) {
                val position = variantViewHolder.adapterPosition
                callback?.onItemClick(itemList[position] as Item.Variant, position)
            }
        }

        return variantViewHolder
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_GROUP -> (holder as GroupViewHolder).onBind(itemList[position] as Item.Group)
            TYPE_VARIANT -> (holder as VariantViewHolder).onBind(itemList[position] as Item.Variant)
        }
    }

    class VariantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun onBind(item: Item.Variant) {
            itemView.variant_name.text = item.name
            itemView.variant_price.text = "₹${item.price}"

            if (item.inStock == 1)
                itemView.variant_in_stock.text = "(In Stock : Yes)"
            else
                itemView.variant_in_stock.text = "(In Stock : No)"

            itemView.selected.isChecked = item.isSelected

        }
    }

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(item: Item.Group) {
            itemView.group_name.text = item.name
        }
    }
}