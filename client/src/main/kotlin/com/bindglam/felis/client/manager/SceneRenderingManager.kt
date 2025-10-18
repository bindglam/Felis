package com.bindglam.felis.client.manager

import com.bindglam.felis.client.rendering.scene.RenderEmptyScene
import com.bindglam.felis.client.rendering.scene.RenderScene
import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.manager.IManager
import com.bindglam.felis.scene.Scene
import com.bindglam.felis.utils.Destroyable

object SceneRenderingManager : IManager, Destroyable {
    private val EMPTY_SCENE = RenderEmptyScene()

    var currentScene: RenderScene = EMPTY_SCENE

    override fun start() {
    }

    fun render() {
        currentScene.render()
    }

    override fun destroy() {
        currentScene.destroy()
    }

    fun changeScene(scene: RenderScene?) {
        this.currentScene.destroy()
        this.currentScene = scene ?: EMPTY_SCENE
    }

    fun changeScene(scene: Scene, shader: Shader) {
        this.currentScene.destroy()
        this.currentScene = RenderScene(scene, shader)
    }
}