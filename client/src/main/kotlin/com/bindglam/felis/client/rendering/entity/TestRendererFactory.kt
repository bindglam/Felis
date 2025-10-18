package com.bindglam.felis.client.rendering.entity

import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.entity.Entity
import com.bindglam.felis.entity.EntityType

class TestRendererFactory : EntityRendererFactory {
    override fun create(entity: Entity, shader: Shader): EntityRenderer {
        return TestRenderer(entity, shader)
    }

    override fun type(): EntityType = EntityType.TEST
}