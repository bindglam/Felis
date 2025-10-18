package com.bindglam.felis.client.io.input

import com.bindglam.felis.client.io.Window
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWKeyCallback

class KeyboardInputHandler(private val window: Window) : GLFWKeyCallback() {
    private val keys = BooleanArray(GLFW.GLFW_KEY_LAST) { false }
    private val justPressed = BooleanArray(GLFW.GLFW_KEY_LAST) { false }
    private val justReleased = BooleanArray(GLFW.GLFW_KEY_LAST) { false }

    private val lastState = BooleanArray(GLFW.GLFW_KEY_LAST) { false }

    fun update() {
        for(i in 0..<GLFW.GLFW_KEY_LAST) {
            justPressed[i] = keys[i] && !lastState[i]
            justReleased[i] = !keys[i] && lastState[i]
            lastState[i] = keys[i]
        }
    }

    override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        if(key >= 0 && key < GLFW.GLFW_KEY_LAST) {
            keys[key] = action == GLFW.GLFW_PRESS
        }
    }

    fun isPressed(key: Int): Boolean = GLFW.glfwGetKey(window.handle, key) == GLFW.GLFW_PRESS
    fun isJustPressed(key: Int): Boolean = justPressed[key]
    fun isJustReleased(key: Int): Boolean = justReleased[key]
}