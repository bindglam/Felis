package com.bindglam.felis.client.rendering.entity

import com.bindglam.felis.client.manager.MasterRenderingManager
import com.bindglam.felis.client.rendering.shader.ShaderType
import com.bindglam.felis.entity.Entity
import com.bindglam.felis.utils.IdentifierLike

class RenderEntity(val entity: Entity, val renderer: EntityRenderer, val shaderType: ShaderType) : IdentifierLike by entity {
    val hitboxRenderer = HitboxRenderer(entity, MasterRenderingManager.shaders[ShaderType.DEBUG]!!)

    fun render() {
        renderer.render()
    }
}