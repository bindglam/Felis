package com.bindglam.felis.client.manager

import com.bindglam.felis.client.rendering.entity.EntityRenderer
import com.bindglam.felis.client.rendering.entity.EntityRendererFactory
import com.bindglam.felis.client.rendering.entity.PlayerRenderer
import com.bindglam.felis.client.rendering.entity.TestRenderer
import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.entity.Entity
import com.bindglam.felis.entity.EntityType
import com.bindglam.felis.manager.IManager
import com.bindglam.felis.utils.Destroyable

object EntityRenderingManager : IManager, Destroyable {
    private val renderers = hashMapOf<EntityType, EntityRendererFactory>()

    init {
        registerRenderer(EntityRendererFactory.builder()
            .create { entity, shader -> TestRenderer(entity, shader) }
            .type(EntityType.TEST)
            .build())

        registerRenderer(EntityRendererFactory.builder()
            .create { entity, shader -> PlayerRenderer(entity, shader) }
            .type(EntityType.PLAYER)
            .build())
    }

    override fun start() {
    }

    override fun destroy() {
    }

    fun createRenderer(entity: Entity, shader: Shader): EntityRenderer {
        return renderers[entity.type]?.create(entity, shader) ?: throw IllegalStateException("Unknown entity type for renderer")
    }

    fun registerRenderer(renderer: EntityRendererFactory) {
        if(renderers.contains(renderer.type()))
            throw IllegalStateException("Already exists")

        renderers[renderer.type()] = renderer
    }
}