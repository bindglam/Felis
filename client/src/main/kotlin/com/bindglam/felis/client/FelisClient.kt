package com.bindglam.felis.client

import com.bindglam.felis.client.io.Window
import com.bindglam.felis.client.rendering.model.EBO
import com.bindglam.felis.client.rendering.model.VAO
import com.bindglam.felis.client.rendering.model.VBO
import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.utils.math.RGBAColor
import org.lwjgl.opengl.GL11
import java.io.File
import kotlin.math.sqrt

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

    private val window = Window("Felis", 1280, 720)

    private constructor()

    override fun run() {
        window.init()

        window.backgroundColor = RGBAColor.of(18, 33, 43, 255)

        val vertices = floatArrayOf(
            -0.5f, -0.5f * sqrt(3f) * 1 / 3, 0.0f,     0.8f, 0.3f,  0.02f, // Lower left corner
            0.5f, -0.5f * sqrt(3f) * 1 / 3, 0.0f,     0.8f, 0.3f,  0.02f, // Lower right corner
            0.0f,  0.5f * sqrt(3f) * 2 / 3, 0.0f,     1.0f, 0.6f,  0.32f, // Upper corner
            -0.25f, 0.5f * sqrt(3f) * 1 / 6, 0.0f,     0.9f, 0.45f, 0.17f, // Inner left
            0.25f, 0.5f * sqrt(3f) * 1 / 6, 0.0f,     0.9f, 0.45f, 0.17f, // Inner right
            0.0f, -0.5f * sqrt(3f) * 1 / 3, 0.0f,     0.8f, 0.3f,  0.02f
        )

        val indices = intArrayOf(
            0, 3, 5,
            3, 2, 4,
            5, 4, 1
        )

        val shader = Shader(File("assets/shaders/default.vert.glsl"), File("assets/shaders/default.frag.glsl"))
        shader.init()

        val vao = VAO()
        vao.bind()

        val vbo = VBO(vertices)
        val ebo = EBO(indices)

        vao.linkVBO(vbo, 0, 3, GL11.GL_FLOAT, 6, 0L)
        vao.linkVBO(vbo, 1, 3, GL11.GL_FLOAT, 6, 3L)

        vao.unbind()
        vbo.unbind()
        ebo.unbind()

        while(!window.shouldClose()) {
            window.clear()

            shader.activate()
            vao.bind()
            GL11.glDrawElements(GL11.GL_TRIANGLES, indices.size, GL11.GL_UNSIGNED_INT, 0)

            window.update()
        }

        vao.destroy()
        vbo.destroy()
        ebo.destroy()
        shader.destroy()

        window.destroy()
    }
}