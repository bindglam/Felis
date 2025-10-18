package com.bindglam.felis.manager

import com.bindglam.felis.scene.Scene
import com.bindglam.felis.scene.Tickable
import com.bindglam.felis.utils.Destroyable

abstract class AbstractSceneManager : IManager, Destroyable, Tickable {
    companion object {
        private val EMPTY_SCENE = Scene()
    }

    var currentScene: Scene = EMPTY_SCENE

    override fun start() {
        // TODO : create new thread for ticking scene
    }

    override fun tick() {
        currentScene.tick()
    }

    override fun destroy() {
    }

    open fun changeScene(scene: Scene?) {
        this.currentScene = scene ?: EMPTY_SCENE
    }
}