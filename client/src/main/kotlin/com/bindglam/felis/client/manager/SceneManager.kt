package com.bindglam.felis.client.manager

import com.bindglam.felis.client.rendering.Renderable
import com.bindglam.felis.client.rendering.scene.RenderScene
import com.bindglam.felis.manager.AbstractSceneManager
import com.bindglam.felis.scene.Scene

object SceneManager : AbstractSceneManager(), Renderable {
    private val EMPTY_SCENE_RENDERER = RenderScene(Scene())

    var currentSceneRenderer: RenderScene = EMPTY_SCENE_RENDERER

    override fun start() {
        super.start()
    }

    override fun tick() {
        super.tick()
    }

    override fun render() {
        currentSceneRenderer.render()
    }

    override fun destroy() {
        super.destroy()

        currentSceneRenderer.destroy()
    }

    fun changeScene(scene: RenderScene?) {
        this.currentSceneRenderer.destroy()

        super.changeScene(scene?.scene)
        this.currentSceneRenderer = scene ?: EMPTY_SCENE_RENDERER
    }

    override fun changeScene(scene: Scene?) {
        changeScene(scene?.let { RenderScene(it) })
    }
}