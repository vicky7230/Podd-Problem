package com.vicky7230.poddproblem.ui.main

sealed class Item {
    data class Group(
        val groupId: String,
        val name: String
    ) : Item()

    data class Variant(
        val groupId: String = "null",
        val name: String = "null",
        val price: Int = -1,
        val default: Int = -1,
        val id: String = "null",
        val inStock: Int = -1,
        var isSelected: Boolean = false
    ) : Item()

    data class Exclusion(
        val groupId: String,
        val variationId: String
    ) : Item()
}