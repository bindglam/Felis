package com.bindglam.felis.client.io.input

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWKeyCallback

class KeyboardInputHandler : GLFWKeyCallback() {
    val keys = BooleanArray(GLFW.GLFW_KEY_LAST) { false }
    val justPressed = BooleanArray(GLFW.GLFW_KEY_LAST) { false }
    val justReleased = BooleanArray(GLFW.GLFW_KEY_LAST) { false }

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
}