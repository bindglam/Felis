package com.bindglam.felis.client

import com.bindglam.felis.client.io.Window
import com.bindglam.felis.client.manager.MasterRenderingManager
import com.bindglam.felis.client.rendering.scene.RenderScene
import com.bindglam.felis.entity.TestEntity
import com.bindglam.felis.scene.Scene
import com.bindglam.felis.utils.math.RGBAColor
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11

class FelisClient : Runnable {
    companion object {
        private var _instance: FelisClient? = null

        val INSTANCE: FelisClient
            get() {
                if(_instance == null)
                    _instance = FelisClient()
                return _instance!!
            }
    }

    private val window = Window("Felis", 1280, 720)

    private constructor()

    override fun run() {
        window.init()

        window.backgroundColor = RGBAColor.of(18, 33, 43, 255)

        MasterRenderingManager.start()

        val scene = Scene()

        val testEntity = TestEntity()
        scene.addEntity(testEntity)

        val renderScene = RenderScene(scene, MasterRenderingManager.defaultShader)

        while(!window.shouldClose()) {
            window.clear()

            testEntity.rotation.y+=1f
            renderScene.render()

            val view = Matrix4f()
            val proj = Matrix4f()
            view.translate(0f, -0.5f, -2.0f)
            proj.perspective(Math.toRadians(45.0).toFloat(), window.size.x.toFloat() / window.size.y, 0.1f, 100.0f)

            renderScene.shader.setUniform("view", view)
            renderScene.shader.setUniform("proj", proj)

            window.update()
        }

        renderScene.destroy()

        MasterRenderingManager.destroy()

        window.destroy()
    }
}