package com.bindglam.felis.manager

import com.bindglam.felis.scene.Scene
import com.bindglam.felis.scene.Tickable
import com.bindglam.felis.utils.Destroyable
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.atomics.AtomicReference
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class, DelicateCoroutinesApi::class)
abstract class AbstractSceneManager : IManager, Destroyable, Tickable {
    companion object {
        private val EMPTY_SCENE = Scene()
    }

    var currentScene = AtomicReference(EMPTY_SCENE)

    private lateinit var tickJob: Job

    override fun start() {
        tickJob = GlobalScope.launch {
            while(true) {
                tick()
                delay((1f/20f*1000L).toLong())
            }
        }
    }

    override fun tick() {
        currentScene.load().tick()
    }

    override fun destroy() {
        tickJob.cancel()
    }

    open fun changeScene(scene: Scene?) {
        currentScene.store(scene ?: EMPTY_SCENE)
    }
}