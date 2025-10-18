package com.bindglam.felis.client.io.input

import com.bindglam.felis.client.io.Window
import org.lwjgl.glfw.GLFW
import org.lwjgl.system.MemoryStack.stackPush

class MouseInputHandler(private val window: Window) {
    private var lastX: Double = 0.0
    private var lastY: Double = 0.0

    private var _dx: Double = 0.0
    private var _dy: Double = 0.0

    val dx: Double
        get() = _dx
    val dy: Double
        get() = _dy

    fun update() {
        val x: Double
        val y: Double

        stackPush().use { stack ->
            val xPos = stack.mallocDouble(1)
            val yPos = stack.mallocDouble(1)

            GLFW.glfwGetCursorPos(window.handle, xPos, yPos)

            x = xPos.get()
            y = yPos.get()
        }

        _dx = x-lastX
        _dy = y-lastY
        lastX = x
        lastY = y
    }
}