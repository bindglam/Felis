package com.bindglam.felis.registry

import com.bindglam.felis.item.Item

object BuiltInRegistries {
    val ITEMS = empty<Item>()

    private fun <T> empty(): Registry<T> = ScalableRegistry<T>()
}