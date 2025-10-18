package com.bindglam.felis.client.rendering.scene

import com.bindglam.felis.client.manager.EntityRenderingManager
import com.bindglam.felis.client.rendering.entity.RenderEntity
import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.scene.Scene
import com.bindglam.felis.utils.Destroyable
import java.util.UUID

open class RenderScene(val scene: Scene, val shader: Shader) : Destroyable {
    val renderers = hashMapOf<UUID, RenderEntity>()

    private val forRemoval = HashSet(renderers.keys)

    open fun render() {
        shader.activate()

        forRemoval.clear()
        forRemoval.addAll(renderers.keys)

        scene.entities.forEach { uuid, entity ->
            val renderer = renderers.computeIfAbsent(uuid) { RenderEntity(entity, EntityRenderingManager.createRenderer(entity, shader)) }

            renderer.render()

            forRemoval.remove(uuid)
        }

        forRemoval.forEach { uuid ->
            renderers.remove(uuid)?.renderer?.destroy()
        }
    }

    override fun destroy() {
        renderers.forEach { _, renderer -> renderer.renderer.destroy() }
        renderers.clear()
    }
}