package com.bindglam.felis.client

import com.bindglam.felis.client.entity.ClientPlayerEntity
import com.bindglam.felis.client.io.Timer
import com.bindglam.felis.client.io.Window
import com.bindglam.felis.client.manager.MasterRenderingManager
import com.bindglam.felis.client.manager.SceneManager
import com.bindglam.felis.entity.LightEntity
import com.bindglam.felis.entity.TestEntity
import com.bindglam.felis.scene.Scene
import com.bindglam.felis.utils.math.RGBAColor
import com.bindglam.felis.utils.math.deg2rad
import org.lwjgl.opengl.GL11
import kotlin.math.cos
import kotlin.math.sin

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

    var isDebugMode = false

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

        val light = LightEntity()
        scene.addEntity(light)

        SceneManager.changeScene(scene)

        var time = 0f
        while(!window.shouldClose()) {
            window.clear()

            //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, if(isDebugMode) GL11.GL_LINE else GL11.GL_FILL)

            testEntity.rotation.rotateY(deg2rad(1f))
            testEntity2.rotation.rotateX(deg2rad(1f))

            light.position.set(cos(time)*3f, 1f, sin(time)*3f)

            MasterRenderingManager.render()

            window.update()
            time += Timer.deltaTime.toFloat()
        }

        MasterRenderingManager.destroy()

        window.destroy()
    }
}