package com.bindglam.felis.client.manager

import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.client.rendering.shader.ShaderType
import com.bindglam.felis.manager.IManager
import com.bindglam.felis.utils.Destroyable
import java.io.File

object MasterRenderingManager : IManager, Destroyable {
    val shaders = linkedMapOf(
        ShaderType.SCENE to Shader(File("assets/shaders/scene.vert.glsl"), File("assets/shaders/scene.frag.glsl")),
        ShaderType.DEBUG to Shader(File("assets/shaders/debug.vert.glsl"), File("assets/shaders/debug.frag.glsl"))
    )

    override fun start() {
        shaders.forEach { _, shader -> shader.init() }

        EntityRenderingManager.start()
        SceneManager.start()
    }

    fun render() {
        SceneManager.render()
    }

    override fun destroy() {
        shaders.forEach { _, shader -> shader.destroy() }

        EntityRenderingManager.destroy()
        SceneManager.destroy()
    }
}