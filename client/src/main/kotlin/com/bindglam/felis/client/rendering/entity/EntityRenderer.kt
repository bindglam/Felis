package com.bindglam.felis.client.rendering.entity

import com.bindglam.felis.utils.Destroyable

interface EntityRenderer : Destroyable {
    fun render()
}