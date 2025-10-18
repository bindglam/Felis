package com.bindglam.felis.client.rendering.entity

import com.bindglam.felis.client.manager.MasterRenderingManager
import com.bindglam.felis.entity.Entity
import com.bindglam.felis.utils.IdentifierLike

class RenderEntity(val entity: Entity, val renderer: EntityRenderer) : IdentifierLike by entity {
    val hitboxRenderer = HitboxRenderer(entity, MasterRenderingManager.debugShader)

    fun render() {
        renderer.render()
    }
}