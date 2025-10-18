package com.bindglam.felis.client

import com.bindglam.felis.client.entity.ClientPlayerEntity
import com.bindglam.felis.client.io.Window
import com.bindglam.felis.client.manager.MasterRenderingManager
import com.bindglam.felis.client.manager.SceneManager
import com.bindglam.felis.entity.TestEntity
import com.bindglam.felis.scene.Scene
import com.bindglam.felis.utils.math.RGBAColor

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

    val window = Window("Felis", 1280, 720)

    private constructor()

    override fun run() {
        window.init()

        window.backgroundColor = RGBAColor.of(18, 33, 43, 255)

        MasterRenderingManager.start()

        val scene = Scene()

        val player = ClientPlayerEntity(true)
        player.position.set(0f, 0.5f, -2.0f)
        scene.addEntity(player)

        val testEntity = TestEntity()
        testEntity.position.set(0f, 1.0f, 2.0f)
        scene.addEntity(testEntity)

        val testEntity2 = TestEntity()
        testEntity2.position.set(1f, 0.0f, 0.0f)
        scene.addEntity(testEntity2)

        SceneManager.changeScene(scene)

        while(!window.shouldClose()) {
            window.clear()

            testEntity.rotation.y+=1f
            testEntity2.rotation.y-=1f
            MasterRenderingManager.render()

            println(testEntity2.hitbox.isColliding(player.hitbox))

            window.update()
        }

        MasterRenderingManager.destroy()

        window.destroy()
    }
}