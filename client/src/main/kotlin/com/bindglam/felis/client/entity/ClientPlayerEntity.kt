package com.bindglam.felis.client.entity

import com.bindglam.felis.client.FelisClient
import com.bindglam.felis.client.io.Timer
import com.bindglam.felis.client.rendering.scene.ICamera
import com.bindglam.felis.entity.AbstractPlayerEntity
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import kotlin.math.cos
import kotlin.math.sin

class ClientPlayerEntity(val isMultiplayerAuthority: Boolean) : AbstractPlayerEntity(), ICamera {
    companion object {
        private const val SPEED = 10f
    }

    var isPaused = true
    var isWireFrameRendering = false

    override fun tick() {
        super.tick()
    }

    override fun updateCamera() {
        val window = FelisClient.INSTANCE.window
        val keyboardHandler = window.keyboardInputHandler
        val mouseHandler = window.mouseInputHandler

        if(isMultiplayerAuthority) {
            if(!isPaused) {
                rotation.y -= mouseHandler.dx.toFloat() * 0.05f
                rotation.x -= mouseHandler.dy.toFloat() * 0.05f
                rotation.x = Math.clamp(rotation.x, -89f, 89f)

                if(keyboardHandler.isPressed(GLFW.GLFW_KEY_W)) {
                    position.add(sin(Math.toRadians(rotation.y.toDouble()).toFloat()) * SPEED * Timer.deltaTime.toFloat(), 0f, cos(Math.toRadians(rotation.y.toDouble()).toFloat()) * SPEED * Timer.deltaTime.toFloat())
                }
                if(keyboardHandler.isPressed(GLFW.GLFW_KEY_S)) {
                    position.add(-sin(Math.toRadians(rotation.y.toDouble()).toFloat()) * SPEED * Timer.deltaTime.toFloat(), 0f, -cos(Math.toRadians(rotation.y.toDouble()).toFloat()) * SPEED * Timer.deltaTime.toFloat())
                }
                if(keyboardHandler.isPressed(GLFW.GLFW_KEY_A)) {
                    position.add(cos(Math.toRadians(rotation.y.toDouble()).toFloat()) * SPEED * Timer.deltaTime.toFloat(), 0f, -sin(Math.toRadians(rotation.y.toDouble()).toFloat()) * SPEED * Timer.deltaTime.toFloat())
                }
                if(keyboardHandler.isPressed(GLFW.GLFW_KEY_D)) {
                    position.add(-cos(Math.toRadians(rotation.y.toDouble()).toFloat()) * SPEED * Timer.deltaTime.toFloat(), 0f, sin(Math.toRadians(rotation.y.toDouble()).toFloat()) * SPEED * Timer.deltaTime.toFloat())
                }

                if(keyboardHandler.isPressed(GLFW.GLFW_KEY_SPACE)) {
                    position.add(0f, SPEED * Timer.deltaTime.toFloat(), 0f)
                }
            }

            if(keyboardHandler.isJustPressed(GLFW.GLFW_KEY_ESCAPE)) {
                isPaused = !isPaused

                GLFW.glfwSetInputMode(window.handle, GLFW.GLFW_CURSOR, if(isPaused) GLFW.GLFW_CURSOR_NORMAL else GLFW.GLFW_CURSOR_DISABLED)
            }

            if(keyboardHandler.isJustPressed(GLFW.GLFW_KEY_F1)) {
                isWireFrameRendering = !isWireFrameRendering

                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, if(isWireFrameRendering) GL11.GL_LINE else GL11.GL_FILL)
            }
        }
    }
}