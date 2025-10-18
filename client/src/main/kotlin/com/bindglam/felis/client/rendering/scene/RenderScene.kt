package com.bindglam.felis.client.rendering.scene

import com.bindglam.felis.client.FelisClient
import com.bindglam.felis.client.manager.EntityRenderingManager
import com.bindglam.felis.client.manager.MasterRenderingManager
import com.bindglam.felis.client.rendering.Renderable
import com.bindglam.felis.client.rendering.entity.RenderEntity
import com.bindglam.felis.client.utils.createViewMatrix
import com.bindglam.felis.scene.Scene
import com.bindglam.felis.utils.Destroyable
import org.joml.Matrix4f
import java.util.*

class RenderScene(val scene: Scene) : Destroyable, Renderable {
    val renderers = hashMapOf<UUID, RenderEntity>()

    private val forRemoval = HashSet(renderers.keys)

    private var projectionMatrix = Matrix4f()
    private var viewMatrix = Matrix4f()

    override fun render() {
        updateMatrix()

        MasterRenderingManager.sceneShader.activate()
        MasterRenderingManager.sceneShader.setUniform("proj", projectionMatrix)
        MasterRenderingManager.sceneShader.setUniform("view", viewMatrix)

        forRemoval.clear()
        forRemoval.addAll(renderers.keys)

        scene.entities.forEach { uuid, entity ->
            val renderer = renderers.computeIfAbsent(uuid) { RenderEntity(entity, EntityRenderingManager.createRenderer(entity, MasterRenderingManager.sceneShader)) }

            renderer.render()

            forRemoval.remove(uuid)
        }

        forRemoval.forEach { uuid ->
            renderers.remove(uuid)?.renderer?.destroy()
        }

        MasterRenderingManager.sceneShader.deactivate()

        MasterRenderingManager.debugShader.activate()
        MasterRenderingManager.debugShader.setUniform("proj", projectionMatrix)
        MasterRenderingManager.debugShader.setUniform("view", viewMatrix)

        renderers.forEach { uuid, renderer ->
            renderer.hitboxRenderer.render()
        }

        MasterRenderingManager.debugShader.deactivate()
    }

    private fun updateMatrix() {
        val camera = scene.entities.values.find { it is ICamera } as ICamera? ?: return
        camera.updateCamera()

        projectionMatrix = FelisClient.INSTANCE.window.projectionMatrix
        viewMatrix = camera.createViewMatrix()
    }

    override fun destroy() {
        renderers.forEach { _, renderer -> renderer.renderer.destroy() }
        renderers.clear()
    }
}