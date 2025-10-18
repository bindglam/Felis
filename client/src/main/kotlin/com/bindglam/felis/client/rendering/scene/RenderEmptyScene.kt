package com.bindglam.felis.client.rendering.scene

import com.bindglam.felis.client.manager.MasterRenderingManager
import com.bindglam.felis.scene.Scene

class RenderEmptyScene : RenderScene(Scene(), MasterRenderingManager.defaultShader) {
    override fun render() {
    }

    override fun destroy() {
    }
}