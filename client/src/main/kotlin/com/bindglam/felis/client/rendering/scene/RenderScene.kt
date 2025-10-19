package com.bindglam.felis.client.rendering.scene

import com.bindglam.felis.client.FelisClient
import com.bindglam.felis.client.manager.EntityRenderingManager
import com.bindglam.felis.client.manager.MasterRenderingManager
import com.bindglam.felis.client.rendering.Renderable
import com.bindglam.felis.client.rendering.entity.RenderEntity
import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.client.rendering.shader.ShaderType
import com.bindglam.felis.client.utils.createViewMatrix
import com.bindglam.felis.entity.LightEntity
import com.bindglam.felis.scene.Scene
import com.bindglam.felis.utils.Destroyable
import org.joml.Matrix4f
import org.joml.Vector3f
import java.util.*

class RenderScene(val scene: Scene) : Destroyable, Renderable {
    val renderers = hashMapOf<UUID, RenderEntity>()

    private val forRemoval = HashSet(renderers.keys)

    private var projectionMatrix = Matrix4f()
    private var viewMatrix = Matrix4f()
    private var cameraPosition = Vector3f()

    override fun render() {
        forRemoval.clear()
        forRemoval.addAll(renderers.keys)

        scene.entities.forEach { uuid, entity ->
            renderers.computeIfAbsent(uuid) { EntityRenderingManager.createRenderer(entity) }

            forRemoval.remove(uuid)
        }

        forRemoval.forEach { uuid ->
            renderers.remove(uuid)?.renderer?.destroy()
        }

        updateMatrix()

        MasterRenderingManager.shaders.forEach { type, shader ->
            shader.activate()
            shader.setUniform("proj", projectionMatrix)
            shader.setUniform("view", viewMatrix)
            shader.setUniform("camPos", cameraPosition)

            renderLighting(type, shader)

            renderers.forEach { uuid, renderer ->
                if(type == ShaderType.DEBUG) {
                    renderer.hitboxRenderer.render()
                }

                if(renderer.shaderType != type) return@forEach

                renderer.render()
            }

            shader.deactivate()
        }
    }

    private fun updateMatrix() {
        val camera = scene.entities.values.find { it is ICamera } as ICamera? ?: return
        camera.updateCamera()

        projectionMatrix = FelisClient.INSTANCE.window.projectionMatrix
        viewMatrix = camera.createViewMatrix()
        cameraPosition = camera.cameraPosition
    }

    private fun renderLighting(type: ShaderType, shader: Shader) {
        if(type == ShaderType.DEBUG) return

        val light = scene.entities.values.find { it is LightEntity } as LightEntity? ?: return

        shader.setUniform("lightColor", light.color)
        shader.setUniform("lightPos", light.position.negate(Vector3f()))
    }

    override fun destroy() {
        renderers.forEach { _, renderer -> renderer.renderer.destroy() }
        renderers.clear()
    }
}