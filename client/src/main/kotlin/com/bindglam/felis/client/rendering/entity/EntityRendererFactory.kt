package com.bindglam.felis.client.rendering.entity

import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.entity.Entity
import com.bindglam.felis.entity.EntityType

interface EntityRendererFactory {
    fun create(entity: Entity, shader: Shader): EntityRenderer

    fun type(): EntityType
}