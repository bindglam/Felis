package com.bindglam.felis.client.io

import com.bindglam.felis.client.io.input.KeyboardInputHandler
import com.bindglam.felis.client.io.input.MouseInputHandler
import com.bindglam.felis.utils.Destroyable
import com.bindglam.felis.utils.math.RGBAColor
import org.joml.Matrix4f
import org.joml.Vector2i
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL

class Window(title: String, private var width: Int, private var height: Int) : Destroyable {
    companion object {
        private val FOV = Math.toRadians(90.0).toFloat()
        private const val Z_NEAR = 0.1f
        private const val Z_FAR = 100f
    }

    private var _handle: Long = 0L
    val handle: Long
        get() = _handle

    var title: String = title
        set(value) {
            field = value

            GLFW.glfwSetWindowTitle(handle, value)
        }

    var size: Vector2i
        get() = Vector2i(width, height)
        set(value) {
            width = value.x
            height = value.y

            GLFW.glfwSetWindowSize(handle, width, height)
        }

    var backgroundColor: RGBAColor = RGBAColor.of(0, 0, 0, 0)
        set(value) {
            field = value

            GL11.glClearColor(value.r() / 255f, value.g() / 255f, value.b() / 255f, value.a() / 255f)
        }

    val projectionMatrix: Matrix4f
        get() = Matrix4f().perspective(FOV, width.toFloat() / height, Z_NEAR, Z_FAR)

    val mouseInputHandler = MouseInputHandler(this)
    val keyboardInputHandler = KeyboardInputHandler(this)

    fun init() {
        GLFWErrorCallback.createPrint(System.err).set()

        if(!GLFW.glfwInit())
            throw IllegalStateException("Unable to initialize GLFW")

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)

        _handle = GLFW.glfwCreateWindow(width, height, title, NULL, NULL)
        if(_handle == NULL)
            throw RuntimeException("Failed to create the GLFW window")

        GLFW.glfwSetWindowSizeCallback(handle) { _, w, h ->
            width = w
            height = h
        }

        GLFW.glfwSetKeyCallback(handle, keyboardInputHandler)

        stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            GLFW.glfwGetWindowSize(handle, pWidth, pHeight)

            val vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())!!

            GLFW.glfwSetWindowPos(
                handle,
                (vidMode.width() - pWidth.get(0)) / 2,
                (vidMode.height() - pHeight.get(0)) / 2
            )
        }

        GLFW.glfwMakeContextCurrent(handle)
        GLFW.glfwSwapInterval(1)

        GLFW.glfwShowWindow(handle)

        GL.createCapabilities()

        GL11.glEnable(GL11.GL_DEPTH_TEST)
    }

    fun shouldClose(): Boolean = GLFW.glfwWindowShouldClose(handle)

    fun clear() {
        GL11.glViewport(0, 0, width, height)
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
    }

    fun update() {
        GLFW.glfwSwapBuffers(handle)
        GLFW.glfwPollEvents()

        mouseInputHandler.update()
        keyboardInputHandler.update()

        Timer.update()
    }

    override fun destroy() {
        Callbacks.glfwFreeCallbacks(handle)
        GLFW.glfwDestroyWindow(handle)

        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)?.free()
    }
}