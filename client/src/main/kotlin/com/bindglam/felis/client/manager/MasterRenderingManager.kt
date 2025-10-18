package com.bindglam.felis.client.manager

import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.manager.IManager
import com.bindglam.felis.utils.Destroyable
import java.io.File

object MasterRenderingManager : IManager, Destroyable {
    val sceneShader = Shader(File("assets/shaders/scene.vert.glsl"), File("assets/shaders/scene.frag.glsl"))

    override fun start() {
        sceneShader.init()

        EntityRenderingManager.start()
        SceneManager.start()
    }

    fun render() {
        SceneManager.render()
    }

    override fun destroy() {
        sceneShader.destroy()

        EntityRenderingManager.destroy()
        SceneManager.destroy()
    }
}