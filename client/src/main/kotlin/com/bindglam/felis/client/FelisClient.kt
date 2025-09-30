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
            -0.5f, -0.5f * sqrt(3f) / 3, 0.0f,
            0.5f, -0.5f * sqrt(3f) / 3, 0.0f,
            0.0f, 0.5f * sqrt(3f) * 2 / 3, 0.0f,
            -0.5f / 2, 0.5f * sqrt(3f) / 6, 0.0f,
            0.5f / 2, 0.5f * sqrt(3f) / 6, 0.0f,
            0.0f, -0.5f * sqrt(3f) / 3, 0.0f
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

        vao.linkVBO(vbo, 0)

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