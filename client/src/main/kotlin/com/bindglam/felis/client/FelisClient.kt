package com.bindglam.felis.client

import com.bindglam.felis.client.io.Window
import com.bindglam.felis.client.rendering.model.EBO
import com.bindglam.felis.client.rendering.model.Texture
import com.bindglam.felis.client.rendering.model.VAO
import com.bindglam.felis.client.rendering.model.VBO
import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.utils.math.RGBAColor
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
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
            -0.5f, -0.5f, 0.0f,     1.0f, 0.0f, 0.0f,	0.0f, 0.0f, // Lower left corner
            -0.5f,  0.5f, 0.0f,     0.0f, 1.0f, 0.0f,	0.0f, 1.0f, // Upper left corner
            0.5f,  0.5f, 0.0f,     0.0f, 0.0f, 1.0f,	1.0f, 1.0f, // Upper right corner
            0.5f, -0.5f, 0.0f,     1.0f, 1.0f, 1.0f,	1.0f, 0.0f
        )

        val indices = intArrayOf(
            0, 2, 1, // Upper triangle
            0, 3, 2
        )

        val shader = Shader(File("assets/shaders/default.vert.glsl"), File("assets/shaders/default.frag.glsl"))
        shader.init()

        val vao = VAO()
        vao.bind()

        val vbo = VBO(vertices)
        val ebo = EBO(indices)

        vao.linkVBO(vbo, 0, 3, GL11.GL_FLOAT, 8, 0L)
        vao.linkVBO(vbo, 1, 3, GL11.GL_FLOAT, 8, 3L)
        vao.linkVBO(vbo, 2, 2, GL11.GL_FLOAT, 8, 6L)

        vao.unbind()
        vbo.unbind()
        ebo.unbind()

        val texture = Texture(File("assets/textures/pop_cat.png"), GL11.GL_TEXTURE_2D, GL13.GL_TEXTURE0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE)
        texture.texUnit(shader, "tex0", 0)

        while(!window.shouldClose()) {
            window.clear()

            shader.activate()
            texture.bind()
            vao.bind()
            GL11.glDrawElements(GL11.GL_TRIANGLES, indices.size, GL11.GL_UNSIGNED_INT, 0)

            window.update()
        }

        vao.destroy()
        vbo.destroy()
        ebo.destroy()
        texture.destroy()
        shader.destroy()

        window.destroy()
    }
}