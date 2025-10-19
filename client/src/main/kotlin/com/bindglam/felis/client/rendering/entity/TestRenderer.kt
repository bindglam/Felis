package com.bindglam.felis.client.rendering.entity

import com.bindglam.felis.client.rendering.model.EBO
import com.bindglam.felis.client.rendering.model.Texture
import com.bindglam.felis.client.rendering.model.VAO
import com.bindglam.felis.client.rendering.model.VBO
import com.bindglam.felis.client.rendering.shader.Shader
import com.bindglam.felis.client.utils.createTransformationMatrix
import com.bindglam.felis.entity.Entity
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import java.io.File

class TestRenderer(val entity: Entity, val shader: Shader) : EntityRenderer {
    companion object {
        private val VERTICES = floatArrayOf(
            -0.5f, 0.0f,  0.5f,     0.83f, 0.70f, 0.44f, 	 0.0f, 0.0f,      0.0f, -1.0f, 0.0f, // Bottom side
            -0.5f, 0.0f, -0.5f,     0.83f, 0.70f, 0.44f,	 0.0f, 5.0f,      0.0f, -1.0f, 0.0f, // Bottom side
            0.5f, 0.0f, -0.5f,     0.83f, 0.70f, 0.44f,	 5.0f, 5.0f,      0.0f, -1.0f, 0.0f, // Bottom side
            0.5f, 0.0f,  0.5f,     0.83f, 0.70f, 0.44f,	 5.0f, 0.0f,      0.0f, -1.0f, 0.0f, // Bottom side

            -0.5f, 0.0f,  0.5f,     0.83f, 0.70f, 0.44f, 	 0.0f, 0.0f,     -0.8f, 0.5f,  0.0f, // Left Side
            -0.5f, 0.0f, -0.5f,     0.83f, 0.70f, 0.44f,	 5.0f, 0.0f,     -0.8f, 0.5f,  0.0f, // Left Side
            0.0f, 0.8f,  0.0f,     0.92f, 0.86f, 0.76f,	 2.5f, 5.0f,     -0.8f, 0.5f,  0.0f, // Left Side

            -0.5f, 0.0f, -0.5f,     0.83f, 0.70f, 0.44f,	 5.0f, 0.0f,      0.0f, 0.5f, -0.8f, // Non-facing side
            0.5f, 0.0f, -0.5f,     0.83f, 0.70f, 0.44f,	 0.0f, 0.0f,      0.0f, 0.5f, -0.8f, // Non-facing side
            0.0f, 0.8f,  0.0f,     0.92f, 0.86f, 0.76f,	 2.5f, 5.0f,      0.0f, 0.5f, -0.8f, // Non-facing side

            0.5f, 0.0f, -0.5f,     0.83f, 0.70f, 0.44f,	 0.0f, 0.0f,      0.8f, 0.5f,  0.0f, // Right side
            0.5f, 0.0f,  0.5f,     0.83f, 0.70f, 0.44f,	 5.0f, 0.0f,      0.8f, 0.5f,  0.0f, // Right side
            0.0f, 0.8f,  0.0f,     0.92f, 0.86f, 0.76f,	 2.5f, 5.0f,      0.8f, 0.5f,  0.0f, // Right side

            0.5f, 0.0f,  0.5f,     0.83f, 0.70f, 0.44f,	 5.0f, 0.0f,      0.0f, 0.5f,  0.8f, // Facing side
            -0.5f, 0.0f,  0.5f,     0.83f, 0.70f, 0.44f, 	 0.0f, 0.0f,      0.0f, 0.5f,  0.8f, // Facing side
            0.0f, 0.8f,  0.0f,     0.92f, 0.86f, 0.76f,	 2.5f, 5.0f,      0.0f, 0.5f,  0.8f  // Facing side
        )

        private val INDICES = intArrayOf(
            0, 1, 2, // Bottom side
            0, 2, 3, // Bottom side
            4, 6, 5, // Left side
            7, 9, 8, // Non-facing side
            10, 12, 11, // Right side
            13, 15, 14 // Facing side
        )

        private val TEXTURE_PATH = File("assets/textures/pop_cat.png")
    }

    val vao = VAO().apply { bind() }
    val vbo = VBO(VERTICES)
    val ebo = EBO(INDICES)

    val texture = Texture(TEXTURE_PATH, GL11.GL_TEXTURE_2D, GL13.GL_TEXTURE0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE)

    init {
        vao.linkVBO(vbo, 0, 3, GL11.GL_FLOAT, 11, 0L)
        vao.linkVBO(vbo, 1, 3, GL11.GL_FLOAT, 11, 3L)
        vao.linkVBO(vbo, 2, 2, GL11.GL_FLOAT, 11, 6L)
        vao.linkVBO(vbo, 3, 3, GL11.GL_FLOAT, 11, 8L)

        vao.unbind()
        vbo.unbind()
        ebo.unbind()

        texture.texUnit(shader, "tex0", 0)
    }

    override fun render() {
        shader.setUniform("model", entity.createTransformationMatrix())

        texture.bind()
        vao.bind()
        GL11.glDrawElements(GL11.GL_TRIANGLES, INDICES.size, GL11.GL_UNSIGNED_INT, 0)
    }

    override fun destroy() {
        vao.destroy()
        vbo.destroy()
        ebo.destroy()

        texture.destroy()
    }
}