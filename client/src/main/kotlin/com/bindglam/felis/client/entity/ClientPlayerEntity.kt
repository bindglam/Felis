package com.bindglam.felis.client.entity

import com.bindglam.felis.client.FelisClient
import com.bindglam.felis.client.rendering.scene.ICamera
import com.bindglam.felis.entity.AbstractPlayerEntity
import org.lwjgl.glfw.GLFW

class ClientPlayerEntity(val isMultiplayerAuthority: Boolean) : AbstractPlayerEntity(), ICamera {
    override fun tick() {
        super.tick()
    }

    override fun updateCamera() {
        val keyboardHandler = FelisClient.INSTANCE.window.keyboardInputHandler
        val mouseHandler = FelisClient.INSTANCE.window.mouseInputHandler

        if(isMultiplayerAuthority) {
            rotation.y += mouseHandler.dx.toFloat() * 0.05f
            rotation.x += mouseHandler.dy.toFloat() * 0.05f
            rotation.x = Math.clamp(rotation.x, -89f, 89f)

            if(keyboardHandler.justReleased[GLFW.GLFW_KEY_ESCAPE]) {
                println("test")
            }
        }
    }
}