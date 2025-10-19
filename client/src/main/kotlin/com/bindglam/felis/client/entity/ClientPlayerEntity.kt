package com.bindglam.felis.client.entity

import com.bindglam.felis.client.FelisClient
import com.bindglam.felis.client.io.Timer
import com.bindglam.felis.client.rendering.scene.ICamera
import com.bindglam.felis.entity.AbstractPlayerEntity
import com.bindglam.felis.utils.math.FRONT
import com.bindglam.felis.utils.math.RIGHT
import com.bindglam.felis.utils.math.deg2rad
import com.bindglam.felis.utils.math.toEulerAngles
import org.joml.Quaternionf
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import kotlin.math.cos
import kotlin.math.sin

class ClientPlayerEntity(val isMultiplayerAuthority: Boolean) : AbstractPlayerEntity(), ICamera {
    companion object {
        private const val SPEED = 5f
    }

    override var cameraPosition = Vector3f()
    override var cameraRotation = Quaternionf()

    var isPaused = true
    var isWireFrameRendering = false

    override fun tick() {
        super.tick()
    }

    override fun updateCamera() {
        cameraPosition = position

        val window = FelisClient.INSTANCE.window
        val keyboardHandler = window.keyboardInputHandler
        val mouseHandler = window.mouseInputHandler

        if(isMultiplayerAuthority) {
            if(!isPaused) {
                val rotY = deg2rad(-mouseHandler.dx.toFloat() * 0.05f)
                val rotX = deg2rad(-mouseHandler.dy.toFloat() * 0.05f)

                rotation.rotateY(rotY)

                val cameraAngles = cameraRotation.toEulerAngles()
                cameraRotation.rotationYXZ(cameraAngles.y+rotY, Math.clamp(cameraAngles.x+rotX, deg2rad(-89f), deg2rad(89f)), 0f)

                //rotation.y -= mouseHandler.dx.toFloat() * 0.05f
                //rotation.x -= mouseHandler.dy.toFloat() * 0.05f
                //rotation.x = Math.clamp(rotation.x, -89f, 89f)

                if(keyboardHandler.isPressed(GLFW.GLFW_KEY_W)) {
                    position.add(rotation.transform(FRONT).mul(SPEED).mul(Timer.deltaTime.toFloat()))
                }
                if(keyboardHandler.isPressed(GLFW.GLFW_KEY_S)) {
                    position.add(rotation.transform(FRONT).mul(-SPEED).mul(Timer.deltaTime.toFloat()))
                }
                if(keyboardHandler.isPressed(GLFW.GLFW_KEY_A)) {
                    position.add(rotation.transform(RIGHT).mul(-SPEED).mul(Timer.deltaTime.toFloat()))
                }
                if(keyboardHandler.isPressed(GLFW.GLFW_KEY_D)) {
                    position.add(rotation.transform(RIGHT).mul(SPEED).mul(Timer.deltaTime.toFloat()))
                }

                if(keyboardHandler.isPressed(GLFW.GLFW_KEY_SPACE)) {
                    position.add(0f, SPEED * Timer.deltaTime.toFloat(), 0f)
                }
                if(keyboardHandler.isPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
                    position.add(0f, -SPEED * Timer.deltaTime.toFloat(), 0f)
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