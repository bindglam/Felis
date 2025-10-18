package com.bindglam.felis.client.manager

import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.manager.IManager
import com.bindglam.felis.utils.Destroyable
import java.io.File

object MasterRenderingManager : IManager, Destroyable {
    val defaultShader = Shader(File("assets/shaders/default.vert.glsl"), File("assets/shaders/default.frag.glsl"))

    override fun start() {
        defaultShader.init()
    }

    override fun destroy() {
        defaultShader.destroy()
    }
}