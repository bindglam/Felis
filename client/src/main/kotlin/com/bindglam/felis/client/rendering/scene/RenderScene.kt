package com.bindglam.felis.client.rendering.scene

import com.bindglam.felis.client.manager.EntityRenderingManager
import com.bindglam.felis.client.manager.MasterRenderingManager
import com.bindglam.felis.client.rendering.Renderable
import com.bindglam.felis.client.rendering.entity.RenderEntity
import com.bindglam.felis.scene.Scene
import com.bindglam.felis.utils.Destroyable
import org.joml.Matrix4f
import org.joml.Vector3f
import java.util.UUID

class RenderScene(val scene: Scene) : Destroyable, Renderable {
    val renderers = hashMapOf<UUID, RenderEntity>()

    private val forRemoval = HashSet(renderers.keys)

    override fun render() {
        MasterRenderingManager.sceneShader.activate()

        forRemoval.clear()
        forRemoval.addAll(renderers.keys)

        scene.entities.forEach { uuid, entity ->
            val renderer = renderers.computeIfAbsent(uuid) { RenderEntity(entity, EntityRenderingManager.createRenderer(entity, MasterRenderingManager.sceneShader)) }

            renderer.render()

            forRemoval.remove(uuid)

            if(entity is ICamera)
                renderCamera(entity)
        }

        forRemoval.forEach { uuid ->
            renderers.remove(uuid)?.renderer?.destroy()
        }
    }

    override fun destroy() {
        renderers.forEach { _, renderer -> renderer.renderer.destroy() }
        renderers.clear()
    }

    private fun renderCamera(camera: ICamera) {
        camera.updateCamera()

        MasterRenderingManager.sceneShader.setUniform("view", camera.createViewMatrix())
    }

    private fun ICamera.createViewMatrix(): Matrix4f = Matrix4f().identity()
        .rotateX(Math.toRadians(-rotation.x.toDouble()).toFloat())
        .rotateY(Math.toRadians(-rotation.y.toDouble()).toFloat())
        .rotateZ(Math.toRadians(-rotation.z.toDouble()).toFloat())
        .translate(position.x, -position.y, position.z)
}